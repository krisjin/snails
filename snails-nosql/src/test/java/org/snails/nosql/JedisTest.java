package org.snails.nosql;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisTest {

	public static void main(String[] args) {

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100000);
		config.setMaxIdle(300);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setTestWhileIdle(true);

		JedisShardInfo jedis2 = new JedisShardInfo("192.168.244.131", 6379);
		JedisShardInfo jedis1 = new JedisShardInfo("192.168.244.133", 6379);

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(jedis1);
		shards.add(jedis2);

		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(config, shards);
		test(shardedJedisPool);

	}

	public static void test(ShardedJedisPool shardedJedisPool) {

		ShardedJedis redis = shardedJedisPool.getResource();

		write(redis);

//		 Set<String> strs = redis.smembers("message");
//		
//		 System.out.println(strs.size());

		shardedJedisPool.returnResource(redis);

	}
	
	
	
	

	public static void write(ShardedJedis redis) {

		long startTime = System.currentTimeMillis();

		String[] arr = new String[1000000];
		for (int i = 0; i < 1000000; i++) {
			arr[i] = "tet" + i;
		}

		redis.sadd("tet", arr);

		long endTime = System.currentTimeMillis() - startTime;

		System.out.println((double) endTime / 1000 + " ms");
		
		
		
		
		
		
		

	}

}
