package org.snails.nosql.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 管理有生存周期的Hash表 注：如果超过生存周期，时间设为0
 * 
 * 作用：存放URL队列，用于判断是否更新，并定期删除超过一定期限的URL。
 * 
 * @author wanggang
 * 
 */
public class ManagerPersistHash implements ManagerHash {

	private static Logger logger = LoggerFactory.getLogger(ManagerPersistHash.class);

	// Hash表的主key
	private final String keyName;

	// 最大时间跨度，用于删除过期数据，毫秒单位
	private final long maxSpanTime;

	// Redis单机器客户端
	private final Jedis jedis;

	/**
	 * 初始化
	 * 
	 * @param jedis
	 *            ：redis客户端
	 * @param keyName
	 *            ：键名
	 * @param maxSpanTime
	 *            ：最大时间跨度，毫秒
	 */
	public ManagerPersistHash(Jedis jedis, String keyName, long maxSpanTime) {
		this.keyName = keyName;
		this.jedis = jedis;
		this.maxSpanTime = maxSpanTime;
	}

	/**
	 * 在集合中插入一条数据，并更新时间为当前的时间
	 */
	@Override
	public void addField(String field) {
		try {
			jedis.hset(keyName, field, System.currentTimeMillis() + "");
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
	 * 判断value是否存在集合中
	 */
	@Override
	public boolean isExisted(String field) {
		try {
			return jedis.hexists(keyName, field);
		} catch (Exception e) {
			// logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return Boolean.FALSE;
		}
	}

	/**
	 * 删除距当前时间超过一定时间的值
	 */
	@Override
	public void delFields() {
		Set<String> fields = jedis.hkeys(keyName);
		List<String> delFields = new ArrayList<String>();
		long currentTime = System.currentTimeMillis();
		for (String field : fields) {
			if (Long.parseLong(jedis.hget(keyName, field)) + maxSpanTime < currentTime) {
				delFields.add(field);
			}
		}
		try {
			jedis.hdel(keyName, delFields.toArray(new String[delFields.size()]));
		} catch (Exception e) {
			// logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	@Override
	public List<String> getFields() {
		Set<String> fields = jedis.hkeys(keyName);
		List<String> result = new ArrayList<String>();
		for (String field : fields) {
			result.add(field);
		}
		return result;
	}

}
