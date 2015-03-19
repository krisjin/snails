package org.snails.nosql;

import java.util.List;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
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

		// 清空所有数据
		// jedis.flushAll();
		System.out.println(jedis.dbSize());

		List list = jedis.mget("name", "age");

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

	}
}
