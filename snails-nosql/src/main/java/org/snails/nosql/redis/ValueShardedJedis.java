package org.snails.nosql.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

/**
 * 根据value值进行分片，原因是：
 *     这里的Redis主要是存放舆情数据的id，所以基本上在少量集和中，
 *     也就是每个集和的数据量很大，而不是集和很多，所以应该针对每个
 *     集和的value进行分片。
 *
 * @author wanggang
 *
 */
public class ValueShardedJedis extends Sharded<Jedis, JedisShardInfo> implements JedisCommands {

	private final Random random = new Random();

	private final Jedis[] allShards;

	private static final Map<String, String> scripts = new HashMap<String, String>();

	public ValueShardedJedis(List<JedisShardInfo> shards) {
		super(shards);
		allShards = getAllShards().toArray(new Jedis[0]);
	}

	public ValueShardedJedis(List<JedisShardInfo> shards, Hashing algo) {
		super(shards, algo);
		allShards = getAllShards().toArray(new Jedis[0]);
	}

	/**
	 * 关闭所有资源
	 */
	public void close() {
		if (allShards != null) {
			for (Jedis jedis : allShards) {
				if (jedis.isConnected()) {
					jedis.disconnect();
				}
				jedis.close();
			}
		}
	}

	/**
	 * added by Jimbo
	 * 存在两种情况
	 * 1：已经存在key，则在已经存在的值结尾加“value”字符串
	 * 2：不存在key，创建key并将value存入key中
	 */
	@Override
	public Long append(String key, String value) {
		Long result = 0L;
		if (this.exists(key)) {
			String newValue = this.get(key) + value;
			this.set(key, newValue);
			result += 1L;
		} else {
			this.set(key, value);
			result += 1L;
		}
		return result;
	}

	/**
	 * added by Jimbo
	 * 经测试
	 */
	@Override
	public Long decr(String key) {
		Long result = 0L;
		if (this.exists(key)) {
			Long newValue = Long.valueOf(this.get(key)).longValue() - 1;
			this.del(key);
			this.set(key, newValue.toString());
			result = newValue.longValue();
		} else {
			this.set(key, "-1");
			result = -1L;
		}
		return result;
	}

	/**
	 * added By Jimbo
	 * 经测试
	 */
	@Override
	public Long decrBy(String key, long integer) {
		Long result = 0L;
		if (this.exists(key)) {
			Long newValue = Long.valueOf(this.get(key)).longValue() - integer;
			this.del(key);
			this.set(key, newValue.toString());
			result = newValue;
		} else {
			this.set(key, "-" + String.valueOf(integer));
			result = -integer;
		}
		return result;
	}

	/**
	 * added by Jimbo
	 *  根据一组key，删除对应的集和。
	 *  对于同一个集和下面的数据可能分布式所有shard上，
	 *  所以需要循环每个shard删除对应的key集和。
	 *  经测试
	 */
	public Long del(String... keys) {
		long result = 0;
		for (Jedis jedis : allShards) {
			result |= jedis.del(keys);
		}
		return result;
	}

	public void eval(String script, String[] keys, String... members) {
		if (members.length == 1) {
			eval(getShard(members[0]), script, keys, members);
		}

		for (Entry<Jedis, List<String>> entry : getShards(members)) {
			eval(entry.getKey(), script, keys, entry.getValue().toArray(new String[entry.getValue().size()]));
		}
	}

	/**
	 * added by Jimbo
	 * 经测试
	 */
	@Override
	public Boolean exists(String key) {
		Boolean result = false;
		for (Jedis jedis : allShards) {
			result = jedis.exists(key);
			if (result == true) {
				break;
			}
		}
		return result;
	}

	/**
	 * added by Jimbo
	 * 为key设置超时时间
	 */
	@Override
	public Long expire(String key, int seconds) {
		Long result = 0L;
		for (Jedis jedis : allShards) {
			result = jedis.expire(key, seconds);
		}
		return result;
	}

	/**
	 * added by jimbo
	 */
	@Override
	public Long expireAt(String key, long unixTime) {
		Long result = 0L;
		for (Jedis jedis : allShards) {
			result = jedis.expireAt(key, unixTime);
		}
		return result;
	}

	/**
	 * added by jimbo
	 * 经测试
	 */
	@Override
	public String get(String key) {
		String result = null;
		for (Jedis jedis : allShards) {
			result = jedis.get(key);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	/**
	 * added by jimbo
	 */
	@Override
	public Boolean getbit(String key, long offset) {
		Boolean result = false;
		for (Jedis jedis : allShards) {
			result = jedis.getbit(key, offset);
		}
		return result;
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		throw new UnsupportedOperationException();
	}

	/**
	 * added by jimbo
	 * 经测试
	 */
	@Override
	public String getSet(String key, String value) {
		String result = null;
		if (this.exists(key)) {
			result = this.get(key);
			this.del(key);
			this.set(key, value);
		} else {
			result = null;
			this.set(key, value);
		}
		return result;
	}

	/**
	 * added by Jimbo
	 * 删除hash存储，中的key-value对
	 */
	@Override
	public Long hdel(String key, String... field) {
		Long result = 0L;
		for (Jedis jedis : allShards) {
			result += jedis.hdel(key, field);
		}
		return result;
	}

	/**
	 * added by Jimbo
	 * 判断hash存储中是否包含某一值
	 * 经测试
	 */
	@Override
	public Boolean hexists(String key, String field) {
		Boolean result = false;
		for (Jedis jedis : allShards) {
			result = jedis.hexists(key, field);
			if (result == true) {
				break;
			}
		}
		return result;
	}

	@Override
	public String hget(String key, String field) {
		String result = null;
		for (Jedis jedis : allShards) {
			result = jedis.hget(key, field);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	public Map<String, String> hgetAll(String key) {
		Map<String, String> result = new HashMap<String,String>();
		Map<String, String> one = null;
		for (Jedis jedis : allShards) {
			one = jedis.hgetAll(key);
			if (one != null) {
				for (Entry<String, String> temp : one.entrySet()) {
					result.put(temp.getKey(), temp.getValue());
				}
			}
		}
		return result;
	}

	/**
	 * added by Jimbo
	 * 这里功能暂时不管
	 */
	@Override
	public Long hincrBy(String key, String field, long value) {
		Long result = 0L;
		if (this.hexists(key, field)) {
			this.hget(key, field);
		} else {
			this.hset(key, field, Long.toString(value));
			result = value;
		}
		return result;
	}

	/**
	 * added by Jimbo
	 * 未测试
	 */
	@SuppressWarnings("null")
	@Override
	public Set<String> hkeys(String key) {
		Set<String> result = null;
		if (this.exists(key)) {
			for (Jedis jedis : allShards) {
				result.addAll(jedis.hkeys(key));
			}
		}
		System.out.println(result);
		return result;
	}

	/**
	 * added by Jimbo
	 * 已测试
	 */
	@Override
	public Long hlen(String key) {
		Long result = 0L;
		for (Jedis jedis : allShards) {
			result += jedis.hlen(key);
		}
		return result;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 已测试
	 */
	@Override
	public Long hset(String key, String field, String value) {
		return getShard(value).hset(key, field, value);
	}

	/**
	 * added by Jimbo
	 * return 如果hash中已经存在该field返回0
	 * 			  如果不存在设置该值为value并返回1
	 * 已测试
	 */
	@Override
	public Long hsetnx(String key, String field, String value) {
		long result = 0L;
		if (!this.hexists(key, field)) {
			this.hset(key, field, value);
			result = 1L;
		}
		return result;
	}

	/**
	 * added by Jimbo
	 * 已测试
	 */
	@Override
	public List<String> hvals(String key) {
		List<String> result = new ArrayList<String>();
		for (Jedis jedis : allShards) {
			result.addAll(jedis.hvals(key));
		}
		return result;
	}

	/**
	 * added by Jimbo
	 *  已测试
	 */
	@Override
	public Long incr(String key) {
		Long result = 0L;
		if (this.exists(key)) {
			Long newValue = Long.valueOf(this.get(key)).longValue() + 1;
			this.del(key);
			this.set(key, newValue.toString());
			result = newValue.longValue();
		} else {
			this.set(key, "1");
			result = 1L;
		}
		return result;
	}

	@Override
	public Long incrBy(String key, long integer) {
		Long result = 0L;
		if (this.exists(key)) {
			Long newValue = Long.valueOf(this.get(key)).longValue() + integer;
			this.del(key);
			this.set(key, newValue.toString());
			result = newValue;
		} else {
			this.set(key, String.valueOf(integer));
			result = integer;
		}
		return result;
	}

	@Override
	public String lindex(String key, long index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long llen(String key) {
		throw new UnsupportedOperationException();
	}

	/**
	 * added by Jimbo
	 *
	 */
	@Override
	public String lpop(String key) {
		// 随机选择一个shard
		Jedis jedis = allShards[random.nextInt(allShards.length)];
		String result = jedis.lpop(key);
		if (result != null) {
			return result;
		}

		// 如果碰巧随机到的shard中没有数据，则继续随机剩下所有的shard
		List<Jedis> js = new ArrayList<Jedis>();
		for (Jedis j : allShards) {
			js.add(j);
		}
		js.remove(jedis);
		return lpop(key, js);
	}

	public String lpop(String key, List<Jedis> jedises) {
		if (jedises.isEmpty()) {
			return null;
		}
		int index = random.nextInt(jedises.size());
		Jedis jedis = jedises.get(index);
		String result = jedis.lpop(key);
		if (result != null) {
			return result;
		}
		jedises.remove(index);
		return lpop(key, jedises);
	}

	/**
	 * 已测试
	 */
	@Override
	public Long lpush(String key, String... members) {
		Long result = (long) members.length;
		if (members.length == 1) {
			getShard(members[0]).lpush(key, members[0]);
		}
		for (Entry<Jedis, List<String>> entry : getShards(members)) {
			entry.getKey().lpush(key, entry.getValue().toArray(new String[entry.getValue().size()]));
		}
		return result;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long lrem(String key, long count, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String lset(String key, long index, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String ltrim(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String rpop(String key) {
		// 随机选择一个shard
		Jedis jedis = allShards[random.nextInt(allShards.length)];
		String result = jedis.rpop(key);
		if (result != null) {
			return result;
		}

		// 如果碰巧随机到的shard中没有数据，则继续随机剩下所有的shard
		List<Jedis> js = new ArrayList<Jedis>();
		for (Jedis j : allShards) {
			js.add(j);
		}
		js.remove(jedis);
		return rpop(key, js);
	}

	/**
	 * added by Jimbo
	 * 已测试
	 */
	@Override
	public Long rpush(String key, String... members) {
		Long result = (long) members.length;
		if (members.length == 1) {
			getShard(members[0]).rpush(key, members[0]);
		}
		for (Entry<Jedis, List<String>> entry : getShards(members)) {
			entry.getKey().rpush(key, entry.getValue().toArray(new String[entry.getValue().size()]));
		}
		return result;
	}

	/**
	 * 已测试
	 */
	@Override
	public Long sadd(String key, String... members) {
		if (members.length == 1) {
			return getShard(members[0]).sadd(key, members[0]);
		}

		long result = 0;
		for (Entry<Jedis, List<String>> entry : getShards(members)) {
			result += entry.getKey().sadd(key, entry.getValue().toArray(new String[entry.getValue().size()]));
		}
		return result;
	}

	/**
	 * 已测试
	 */
	@Override
	public Long scard(String key) {
		long result = 0;
		for (Jedis jedis : allShards) {
			result += jedis.scard(key);
		}
		return result;
	}

	/**
	 * added by Jimbo
	 *  经测试
	 */
	@Override
	public String set(String key, String value) {
		String result = null;
		result = getShard(value).set(key, value);
		return result;
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String setex(String key, int seconds, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long setnx(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 已测试
	 */
	@Override
	public Boolean sismember(String key, String member) {
		return getShard(member).sismember(key, member);
	}

	/**
	 * 已测试
	 */
	@Override
	public Set<String> smembers(String key) {
		Set<String> result = new HashSet<String>();
		for (Jedis jedis : allShards) {
			result.addAll(jedis.smembers(key));
		}
		return result;
	}

	@Override
	public List<String> sort(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 已测试
	 */
	@Override
	public String spop(String key) {
		// 随机选择一个shard
		Jedis jedis = allShards[random.nextInt(allShards.length)];
		String result = jedis.spop(key);
		if (result != null) {
			return result;
		}

		// 如果碰巧随机到的shard中没有数据，则继续随机剩下所有的shard
		List<Jedis> js = new ArrayList<Jedis>();
		for (Jedis j : allShards) {
			js.add(j);
		}
		js.remove(jedis);
		return spop(key, js);
	}

	@Override
	public String srandmember(String key) {
		// 随机选择一个shard
		Jedis jedis = allShards[random.nextInt(allShards.length)];
		String result = jedis.srandmember(key);
		if (result != null) {
			return result;
		}

		// 如果碰巧随机到的shard中没有数据，则继续随机剩下所有的shard
		List<Jedis> js = new ArrayList<Jedis>();
		for (Jedis j : allShards) {
			js.add(j);
		}
		js.remove(jedis);
		return srandmember(key, js);
	}

	/**
	 * 已测试
	 */
	@Override
	public Long srem(String key, String... members) {
		if (members.length == 1) {
			return getShard(members[0]).srem(key, members[0]);
		}

		long result = 0;
		for (Entry<Jedis, List<String>> entry : getShards(members)) {
			result += entry.getKey().srem(key, entry.getValue().toArray(new String[entry.getValue().size()]));
		}
		return result;
	}

	@Override
	public String substr(String key, int start, int end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long ttl(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String type(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zadd(String key, double score, String member) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zcard(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zcount(String key, double min, double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zcount(String key, String min, String max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zrank(String key, String member) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zrem(String key, String... member) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zrevrank(String key, String member) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double zscore(String key, String member) {
		throw new UnsupportedOperationException();
	}

	private Object eval(Jedis jedis, String script, String[] keys, String... members) {
		String[] params = new String[keys.length + members.length];
		System.arraycopy(keys, 0, params, 0, keys.length);
		System.arraycopy(members, 0, params, keys.length, members.length);
		String sha = scripts.get(script);
		if (sha == null) {
			sha = DigestUtils.shaHex(script);
			scripts.put(script, sha);
		}
		try {
			return jedis.evalsha(sha, keys.length, params);
		} catch (JedisDataException e) {
			return jedis.eval(script, keys.length, params);
		}
	}

	private Set<Entry<Jedis, List<String>>> getShards(String[] members) {
		Map<Jedis, List<String>> jedisMembersMap = new HashMap<Jedis, List<String>>();
		for (String member : members) {
			Jedis jedis = getShard(member);
			List<String> ms = jedisMembersMap.get(jedis);
			if (ms == null) {
				ms = new ArrayList<String>();
				jedisMembersMap.put(jedis, ms);
			}
			ms.add(member);
		}
		return jedisMembersMap.entrySet();
	}

	private String rpop(String key, List<Jedis> jedises) {
		if (jedises.isEmpty()) {
			return null;
		}
		int index = random.nextInt(jedises.size());
		Jedis jedis = jedises.get(index);
		String result = jedis.rpop(key);
		if (result != null) {
			return result;
		}
		jedises.remove(index);
		return rpop(key, jedises);
	}

	/**
	 * 随机删除一个值
	 */
	private String spop(String key, List<Jedis> jedises) {
		if (jedises.isEmpty()) {
			return null;
		}
		int index = random.nextInt(jedises.size());
		Jedis jedis = jedises.get(index);
		String result = jedis.spop(key);
		if (result != null) {
			return result;
		}
		jedises.remove(index);
		return spop(key, jedises);
	}

	private String srandmember(String key, List<Jedis> jedises) {
		if (jedises.isEmpty()) {
			return null;
		}
		int index = random.nextInt(jedises.size());
		Jedis jedis = jedises.get(index);
		String result = jedis.srandmember(key);
		if (result != null) {
			return result;
		}
		jedises.remove(index);
		return srandmember(key, jedises);
	}

	@Override
	public Long persist(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long strlen(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long lpushx(String key, String... string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long rpushx(String key, String... string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> blpop(String arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> brpop(String arg) {
		throw new UnsupportedOperationException();
	}

	/**
	 * added by Jimbo
	 */
	@Override
	public Long del(String key) {
		Long result = 0L;
		for (Jedis jedis : allShards) {
			result = jedis.del(key);
		}
		return result;
	}

	@Override
	public String echo(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long move(String key, int dbIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long bitcount(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String set(String key, String value, String nxxx, String expx, long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pfadd(String key, String... elements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long pfcount(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> blpop(int arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> brpop(int arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> srandmember(String arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zlexcount(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByLex(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByLex(String arg0, String arg1, String arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByLex(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
