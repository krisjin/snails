package org.snails.redis;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

/**
 * 基于值分片的Redis池
 * 
 * @author wanggang
 * 
 */
public class ValueShardedJedisPool extends Pool<ValueShardedJedis> {

//	private static Logger logger = LoggerFactory.getLogger(ValueShardedJedisPool.class);

	public ValueShardedJedisPool(final GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards) {
		this(poolConfig, shards, Hashing.MURMUR_HASH);
	}

	public ValueShardedJedisPool(final GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo) {
		this(poolConfig, shards, algo, null);
	}

	public ValueShardedJedisPool(final GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards,
			Pattern keyTagPattern) {
		this(poolConfig, shards, Hashing.MURMUR_HASH, keyTagPattern);
	}

	public ValueShardedJedisPool(final GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo,
			Pattern keyTagPattern) {
		super(poolConfig, new ValueShardedJedisFactory(shards, algo, keyTagPattern));
	}

	/**
	 * PoolableObjectFactory custom impl.
	 */
	private static class ValueShardedJedisFactory implements PooledObjectFactory<ValueShardedJedis> {

		private final List<JedisShardInfo> shards;
		private final Hashing algo;

		// private final Pattern keyTagPattern;

		public ValueShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
			this.shards = shards;
			this.algo = algo;
			// this.keyTagPattern = keyTagPattern;
		}

		@Override
		public PooledObject<ValueShardedJedis> makeObject() throws Exception {
			ValueShardedJedis jedis = new ValueShardedJedis(shards, algo);
			return new DefaultPooledObject<ValueShardedJedis>(jedis);
		}

		@Override
		public void destroyObject(PooledObject<ValueShardedJedis> pooledShardedJedis) throws Exception {
			final ValueShardedJedis shardedJedis = pooledShardedJedis.getObject();
			for (Jedis jedis : shardedJedis.getAllShards()) {
				try {
					try {
						jedis.quit();
					} catch (Exception e) {
						// logger.error("Exception:{}",
						// LogbackUtil.expection2Str(e));
					}
					jedis.disconnect();
				} catch (Exception e) {
					// logger.error("Exception:{}",
					// LogbackUtil.expection2Str(e));
				}
			}
		}

		@Override
		public boolean validateObject(PooledObject<ValueShardedJedis> pooledShardedJedis) {
			try {
				ValueShardedJedis jedis = pooledShardedJedis.getObject();
				for (Jedis shard : jedis.getAllShards()) {
					if (!shard.ping().equals("PONG")) {
						return false;
					}
				}
				return true;
			} catch (Exception e) {
//				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				return false;
			}
		}

		@Override
		public void activateObject(PooledObject<ValueShardedJedis> p) throws Exception {
			//
		}

		@Override
		public void passivateObject(PooledObject<ValueShardedJedis> p) throws Exception {
			//
		}

		@Override
		public String toString() {
			return "ShardedJedisFactory [shards=" + shards + "]";
		}

	}

	@Override
	public ValueShardedJedis getResource() {
		ValueShardedJedis jedis = super.getResource();
		return jedis;
	}

	@Override
	public void returnBrokenResource(final ValueShardedJedis resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}

	@Override
	public void returnResource(final ValueShardedJedis resource) {
		if (resource != null) {
			resource.close();
			returnResourceObject(resource);
		}
	}

}