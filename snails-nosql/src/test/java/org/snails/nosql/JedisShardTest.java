package org.snails.nosql;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class JedisShardTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test();

	}

	public static void test() {
//		JedisShardInfo shard1 = new JedisShardInfo("192.168.244.133", 6379, 300000);
		JedisShardInfo shard2 = new JedisShardInfo("192.168.244.131", 6379, 300000);

		long startTime = System.currentTimeMillis();

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

//		shards.add(shard1);
		shards.add(shard2);

		ShardedJedis sharding = new ShardedJedis(shards);

//		for (int i = 0; i < 3000000; i++) {
//			sharding.set("sharding" + 1, "vs" + 1);
//
//		}
//
//		long endTime = System.currentTimeMillis();

//		System.out.println("cost time " + (endTime - startTime) / 1000.0 + "ms");
		System.out.println(sharding.get("sharding1"));
//		System.out.println(sharding.d);

	}
}
