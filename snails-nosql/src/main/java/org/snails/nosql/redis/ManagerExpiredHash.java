package org.snails.nosql.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 管理有生存周期的Hash表 注：如果超过生存周期，时间设为0
 * 
 * 作用：存放带有生存周期的URL列表，并定期删除超过一定期限的URL；
 * 
 * 设计思路：通过1张Hash表和1个Set集合来实现，
 * 1、Hash表存放所有的URL，不带有生存周期，field是URL，value是上一次使用的时间lasttime；
 * 2、Set集合存放所有的最新的URL或者需要抓取的所有URL，并带有生存周期，过期的URL在Redis内部会自动删除；
 * 3、每次add一个URL的时候，同时往Hash和Set里面add；
 * 4、每次获取生存周期为0的URL列表时，将Hash-Set（即diff(hash,set)）就可以得到，
 * 同时还需要将这个列表重新加入Set中，并设置同样的生存周期； 5、定时根据Hash表中的每条记录的lasttime与当前时间的差值删除该条记录。
 * 
 * 具体实现细节： 1、使用HSET设置URL:lasttime键值对，以及更新lasttime； 2、使用SET设置URL:constant键值对；
 * 3、使用EXPIRE设置URL:expire键值对； 4、使用SCAN匹配出所有的URL，或者使用KEYS命令；
 * 5、使用HKEY获取所有field名，并与4中的URL列表做差集运算，得到过期的URL列表；
 * 6、在1中更新5中得到的URL的lasttime，并对这些URL进行2操作； 7、返回5中的列表。
 * 
 * 这里的URL是指URL的MD5值。
 * 
 */
public class ManagerExpiredHash implements ManagerHash {

	private static Logger logger = LoggerFactory.getLogger(ManagerExpiredHash.class);

	private static final String ONE = "1";

	// Hash表的主key
	private final String keyName;

	// Redis单机器客户端
	private final Jedis jedis;

	// 生存周期，毫秒
	private final long expire;

	// 最大时间跨度，用于删除过期数据，毫秒单位
	private final long maxSpanTime;

	public ManagerExpiredHash(Jedis jedis, String keyName, long expire, long maxSpanTime) {
		this.jedis = jedis;
		this.keyName = keyName;
		this.expire = expire;
		this.maxSpanTime = maxSpanTime;
	}

	/**
	 * 1、在集合中插入一条数据，并更新为生存周期
	 */
	@Override
	public void addField(String field) {
		try {
			jedis.hset(keyName, field, System.currentTimeMillis() + "");
			// 2、同时设置该field的生存周期
			jedis.set(field, ONE);
			jedis.pexpire(field, expire);
		} catch (Exception e) {
			// logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	@Override
	public void addFields(String... fields) {
		for (String field : fields) {
			addField(field);
		}
	}

	/**
	 * 获取生存周期为0的数据列表
	 */
	@Override
	public List<String> getFields() {
		// Hash表中的所有field，即所有URL
		Set<String> allFields = jedis.hkeys(keyName);
		// Set中的key，即尚未达到过期时间的URL
		Set<String> allKeys = jedis.keys("*");
		// 提取差集
		List<String> result = new ArrayList<String>();
		for (String field : allFields) {
			if (!allKeys.contains(field)) {
				result.add(field);
				// 同时更新lasttime：若更新的话，几乎都是最新时间，所以暂不更新
				// jedis.hset(keyName, field, System.currentTimeMillis() + "");
				// expire重置
				jedis.pexpire(field, expire);
			}
		}

		return result;
	}

	/**
	 * 判断URL在Hash表中是否存在
	 */
	@Override
	public boolean isExisted(String field) {
		return jedis.hexists(keyName, field);
	}

	/**
	 * 删除Hash表中，根据存入时间和当前的时间差来删除
	 */
	@Override
	public void delFields() {
		final long current = System.currentTimeMillis();
		List<String> fields = new ArrayList<String>();
		Map<String, String> fieldAndKeys = jedis.hgetAll(keyName);
		for (Entry<String, String> fieldAndKey : fieldAndKeys.entrySet()) {
			if (Long.parseLong(fieldAndKey.getValue()) + maxSpanTime < current) {
				fields.add(fieldAndKey.getKey());
			}
		}
		jedis.hdel(keyName, fields.toArray(new String[fields.size()]));
	}

}
