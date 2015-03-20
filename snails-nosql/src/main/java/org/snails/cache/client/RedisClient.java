package org.snails.cache.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snails.cache.RedisCache;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient implements RedisCache {

	private ShardedJedisPool jedisPool;

	private static Logger logger = LoggerFactory.getLogger(RedisClient.class);

	public RedisClient(ShardedJedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public Long del(String... keys) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.del("");
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

	@Override
	public Long del(String key) {

		ShardedJedis jedis = jedisPool.getResource();

		try {
			jedis.del(key);
		} finally {
			jedisPool.returnResource(jedis);
		}

		return null;
	}

	@Override
	public boolean exists(String key) {
		ShardedJedis jedis = jedisPool.getResource();

		try {
			return jedis.exists(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public void eval(String script, String[] keys, String... members) {

	}

	@Override
	public void sadd(String key, String... members) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			jedis.sadd(key, members);
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

	@Override
	public Long scard(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public boolean sismember(String key, String member) {

		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.sismember(key, member);
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

	@Override
	public Set<String> smembers(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.smembers(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public String spop(String key) {
		ShardedJedis jedis = jedisPool.getResource();

		try {
			return jedis.spop(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public String srandmember(String key) {

		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.srandmember(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public List<String> srandmember(String key, int count) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.srandmember(key, count);
		} finally {

			jedisPool.returnResource(jedis);
		}

	}

	@Override
	public Long srem(String key, String... members) {

		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.srem(key, members);
		} finally {

			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public Long hset(String key, String field, String value) {

		return null;
	}

	@Override
	public Long lpush(String key, String... members) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.lpushx(key, members);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public String rpop(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.rpop(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public String hget(String key, String field) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.hget(key, field);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.hgetAll(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public void close() {

		jedisPool.close();
	}

}
