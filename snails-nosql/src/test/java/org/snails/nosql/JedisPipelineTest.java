package org.snails.nosql;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class JedisPipelineTest {

	public static void main(String[] args) {
		test();
	}

	public static void test() {

		Jedis jedis = new Jedis("192.168.244.133", 6379, 300000);
		long startTime = System.currentTimeMillis();

		Pipeline pipeLine = jedis.pipelined();

		for (int i = 0; i < 2000000; i++) {

			pipeLine.set("p" + i, "p" + i);

		}
		List<Object> list = pipeLine.syncAndReturnAll();

		long endTime = System.currentTimeMillis();

		System.out.println("cost time " + (endTime - startTime) / 1000.0 + " ms");
		System.out.println(list.size());

		jedis.disconnect();
	}
}
