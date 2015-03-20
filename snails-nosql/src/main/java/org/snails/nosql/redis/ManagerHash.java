package org.snails.nosql.redis;

import java.util.List;

/**
 * 管理Hash表的接口
 * 
 * @author wanggang
 *
 */
public interface ManagerHash {

	public void addField(String field);

	public void addFields(String... fields);

	public boolean isExisted(String field);

	public List<String> getFields();

	public void delFields();

}
