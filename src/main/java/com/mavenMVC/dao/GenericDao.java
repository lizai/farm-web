package com.mavenMVC.dao;

import com.mavenMVC.core.Page;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, PK extends Serializable> {

	List<T> getAll();

	List<T> getAllDistinct();

	T get(PK id);

	boolean exists(PK id);

	Serializable save(T object);

	void remove(PK id);

	List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

	/**
	 * @param queryString
	 * @return
	 */
	List<T> find(String queryString);

	List<T> findByDetachedCriteria(final DetachedCriteria criteria);

	List<T> findPageByDetachedCriteria(final DetachedCriteria criteria,
									   int from, int size);

	/**
	 * @param object
	 */
	void update(T object);

	void saveOrUpdate(T object);

	/**
	 * @param page
	 * @param hql
	 * @param values
	 * @return
	 */
	Page<T> findPage(Page<T> page, String hql, Object... values);

	/**
	 * 根据hql查询总数
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	int count(String hql, Object... values);
	
	void update(String hql);
}
