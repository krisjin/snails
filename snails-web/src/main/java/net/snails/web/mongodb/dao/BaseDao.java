package net.snails.web.mongodb.dao;

import java.util.List;

import net.snails.web.util.ReflectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author krisjin
 * @date 2014-11-4
 */
public abstract class BaseDao<T> implements IDao<T> {

	@Autowired
	private MongoTemplate mongoTemplate;

	protected Class<T> entityClass;

	public BaseDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	public long count() {

		return this.mongoTemplate.count(new Query(), entityClass);
	}

	public void save(T entity) {
		this.mongoTemplate.insert(entity);
	}

	public void delete(T entity) {
		this.mongoTemplate.remove(entity);
	}

	public void deleteById(String id) {

	}

	public void deleteAll() {
		this.mongoTemplate.remove(new Query(), entityClass);
	}

	public void update(T enttiy) {
		this.mongoTemplate.save(enttiy);
	}

	public List<T> getAll() {

		return (List<T>) mongoTemplate.find(new Query(), entityClass);
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
