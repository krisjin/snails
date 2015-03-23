package org.snails.nosql;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

public class JedisTest {
	private static Jedis jedis;
	private static ShardedJedis sharding;
	private static ShardedJedisPool pool;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		JedisShardInfo shard1 = new JedisShardInfo("192.168.244.133", 6379, 300000);
		JedisShardInfo shard2 = new JedisShardInfo("192.168.244.131", 6379, 300000);

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(shard1);
		shards.add(shard2);

		jedis = new Jedis("localhost");
		sharding = new ShardedJedis(shards);

		pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		jedis.disconnect();
		sharding.disconnect();
		pool.destroy();
	}

	@Test
	public void test1Normal() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String result = jedis.set("n" + i, "n" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("Simple SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test2Trans() {
		long start = System.currentTimeMillis();
		Transaction tx = jedis.multi();
		for (int i = 0; i < 100000; i++) {
			tx.set("t" + i, "t" + i);
		}
		// System.out.println(tx.get("t1000").get());

		List<Object> results = tx.exec();
		long end = System.currentTimeMillis();
		System.out.println("Transaction SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test3Pipelined() {
		Pipeline pipeline = jedis.pipelined();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			pipeline.set("p" + i, "p" + i);
		}
		// System.out.println(pipeline.get("p1000").get());
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("Pipelined SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test4combPipelineTrans() {
		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		for (int i = 0; i < 100000; i++) {
			pipeline.set("" + i, "" + i);
		}
		pipeline.exec();
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("Pipelined transaction: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test5shardNormal() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String result = sharding.set("sn" + i, "n" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("Simple@Sharing SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test6shardpipelined() {
		ShardedJedisPipeline pipeline = sharding.pipelined();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			pipeline.set("sp" + i, "p" + i);
		}
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("Pipelined@Sharing SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test7shardSimplePool() {
		ShardedJedis one = pool.getResource();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String result = one.set("spn" + i, "n" + i);
		}
		long end = System.currentTimeMillis();
		pool.returnResource(one);
		System.out.println("Simple@Pool SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test8shardPipelinedPool() {
		ShardedJedis one = pool.getResource();

		ShardedJedisPipeline pipeline = one.pipelined();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			pipeline.set("sppn" + i, "n" + i);
		}
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		pool.returnResource(one);
		System.out.println("Pipelined@Pool SET: " + ((end - start) / 1000.0) + " seconds");
	}
}
