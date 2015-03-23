package org.snails.nosql;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class JedisTransactionsTest {

	public static void main(String[] args) {
		test();
	}

	/**
	 * 
	 */
	public static void test() {

		Jedis jedis = new Jedis("192.168.244.133", 6379, 300000);

		long startTime = System.currentTimeMillis();

		Transaction trans = jedis.multi();

		for (int i = 0; i < 2000000; i++) {
			trans.set("k" + i, "v" + i);
		}
		// trans.flushAll();

		trans.exec();
		long costTime = System.currentTimeMillis() - startTime;

		System.out.println(costTime / 1000.0 + " ms");
		jedis.disconnect();
	}
}
