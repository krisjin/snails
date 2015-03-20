package org.snails.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis cache
 * 
 * @author krisjin
 * @date 2015-3-20
 */
public interface RedisCache extends Cache {

	/**
	 * Remove one or more key, a non-existent key will be ignored
	 * 
	 * @param keys
	 * @return
	 */
	Long del(String... keys);

	Long del(String key);

	/**
	 * check the key exists
	 * 
	 * @param key
	 * @return exists=1,non-exists=0
	 */
	boolean exists(String key);

	/**
	 * 内置的Lua解释器，可以使用EVAL命令对Lua脚本进行求值。
	 * 
	 * @param script
	 *            脚本
	 * @param keys
	 *            关键词
	 * @param members
	 *            对应值
	 * @return
	 */
	void eval(String script, String[] keys, String... members);

	/**
	 * One or more member is One or more members of the set of key elements to
	 * them, already exists in the collection of member elements are ignored.
	 * 
	 * @param key
	 * @param members
	 */
	void sadd(String key, String... members);

	/**
	 * 返回集合key的基数(集合中元素的数量)。
	 * 
	 * @param key
	 *            关键词
	 * @return
	 */
	Long scard(String key);

	/**
	 * 判断member元素是否集合 key 的成员。
	 * 
	 * @param key
	 *            关键词
	 * @param member
	 *            待查询值
	 * @return
	 */
	boolean sismember(String key, String member);

	/**
	 * 返回集合key中的所有成员。
	 * 
	 * @param key
	 *            关键词
	 * @return
	 */
	Set<String> smembers(String key);

	/**
	 * 移除并返回集合中的一个随机元素。
	 * 
	 * @param key
	 * @return
	 */
	String spop(String key);

	/**
	 * 返回随机元素，而不对集合进行任何改动。
	 * 
	 * @param key
	 * @return
	 */
	String srandmember(String key);

	List<String> srandmember(String key, int count);

	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
	 * 
	 * @param key
	 *            关键词
	 * @param members
	 *            值列表
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 */
	Long srem(String key, String... members);

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域
	 * field 已经存在于哈希表中，旧值将被覆盖。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);

	/**
	 * 将一个或多个member元素加到List key当中，全部都会写入
	 * 
	 * @param key
	 * @param members
	 *            对应值
	 */
	Long lpush(String key, String... members);

	/**
	 * 移除并返回List中的一个数
	 * 
	 * @param key
	 */
	String rpop(String key);

	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);

	/**
	 * 返回Hash表中的所有键值
	 * 
	 * @param key
	 *            ：键名
	 * @return
	 */
	Map<String, String> hgetAll(String key);

	public void close();

}
