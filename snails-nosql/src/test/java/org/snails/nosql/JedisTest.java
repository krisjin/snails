package org.snails.nosql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class JedisTest {

	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100000);

		JedisShardInfo info = new JedisShardInfo("192.168.244.133", 6379);

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(info);

		ShardedJedisPool ddd = new ShardedJedisPool(config, shards);
		ddd.getResource();
		test();
	}

	public static void test() {

		// 连接
		Jedis jedis = new Jedis("192.168.244.133", 6379);

		// 密码验证
		// jedis.auth("");

		// 设置值
		jedis.set("name", "krisjin");
		jedis.set("age", "29");
		jedis.set("address", "Beijing");

		jedis.mset("mobile", "12334568789", "duty", "high");

		jedis.setex("person", 10, "fsdfda");

		String[] comm = new String[] { "good", "bad" };

		jedis.sadd("comments", comm);

		List list = jedis.mget("name", "age");

		System.out.println(jedis.dbSize());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		Set<String> sets = jedis.smembers("comments");

		for (String str : sets) {
			System.out.println(str);
		}

		System.out.println(jedis.scard("comments"));

		Long p = jedis.hset("user", "name", "jingui");

		String v = jedis.hget("user", "name");

		System.out.println(v);

	}
}
