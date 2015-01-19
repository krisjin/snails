package net.snails.web.mongodb.dao;

import java.util.List;

/**
 * @author krisjin
 * @date 2014-11-4
 */
public interface IDao<T> {

	public long count();

	public void save(T entity);

	public void delete(T entity);

	public void deleteById(String id);

	public void deleteAll();

	public void update(T enttiy);

	public List<T> getAll();

}
