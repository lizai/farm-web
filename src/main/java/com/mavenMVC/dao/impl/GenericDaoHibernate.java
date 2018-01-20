package com.mavenMVC.dao.impl;

import com.mavenMVC.core.Page;
import com.mavenMVC.dao.GenericDao;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;


public class GenericDaoHibernate<T, PK extends Serializable> implements
		GenericDao<T, PK> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private Class<T> persistentClass;

	private HibernateTemplate hibernateTemplate;

	private SessionFactory sessionFactory;

	/**
	 * 构造函数，确定当前的实体类是哪一个
	 * 
	 * @param persistentClass
	 *            实体类
	 */
	public GenericDaoHibernate(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	/**
	 * 带有class和sessionFactory两个参数的构造函数，方便生成DAO
	 * 
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public GenericDaoHibernate(final Class<T> persistentClass,
			SessionFactory sessionFactory) {
		this.persistentClass = persistentClass;
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		log.info("set sesssionFactory" + sessionFactory);
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public List<T> getAll() {
		return hibernateTemplate.loadAll(this.persistentClass);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> getAllDistinct() {
		Collection result = new LinkedHashSet(getAll());
		return new ArrayList(result);
	}

	@Override
	public T get(PK id) {
		T entity = (T) hibernateTemplate.get(this.persistentClass, id);

		if (entity == null) {
			log.warn("Uh oh, '" + this.persistentClass + "' object with id '"
					+ id + "' not found...");
			throw new ObjectRetrievalFailureException(this.persistentClass, id);
		}

		return entity;
	}

	@Override
	public boolean exists(PK id) {
		T entity = (T) hibernateTemplate.get(this.persistentClass, id);
		return entity != null;
	}

	@Override
	public Serializable save(T object) {
		return hibernateTemplate.save(object);
	}

	@Override
	public void update(T object) {
		hibernateTemplate.update(object);
		hibernateTemplate.flush();
	}

	@Override
	public void saveOrUpdate(T object) {
		hibernateTemplate.saveOrUpdate(object);
		hibernateTemplate.flush();
	}

	@Override
	public void remove(PK id) {
		hibernateTemplate.delete(this.get(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String queryString) {
		return hibernateTemplate.find(queryString);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(final String queryString, final Object... values) {
		Query q = createQuery(queryString, values);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public T findUnique(final String queryString, final Object... values) {
		Query q = createQuery(queryString, values);
		return (T) q.uniqueResult();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> findByDetachedCriteria(final DetachedCriteria criteria) {
		return (List<T>) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria1 = criteria
								.getExecutableCriteria(session);
						return criteria1.list();
					}
				});
	}

	@Override
	public List<T> findPageByDetachedCriteria(final DetachedCriteria criteria,
			final int from, final int size) {
		return (List<T>) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria1 = criteria
								.getExecutableCriteria(session);
						if (from >= 0 && size > 0)
							return criteria1.setFirstResult(from)
									.setMaxResults(size).list();
						else
							return criteria1.list();
					}
				});
	}

	@Override
	public int count(String hql, Object... values) {
		Query q = createQuery(hql, values);
		return (Integer) q.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		String[] params = new String[queryParams.size()];
		Object[] values = new Object[queryParams.size()];

		int index = 0;
		for (String s : queryParams.keySet()) {
			params[index] = s;
			values[index++] = queryParams.get(s);
		}

		return hibernateTemplate.findByNamedQueryAndNamedParam(queryName,
				params, values);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定. 注意：page在传进来之时需要行将totalCount计算好
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values) {
//		Preconditions.checkNotNull(page);

		Query q = createQuery(hql, values);
		setPageParameter(q, page);
		List<T> result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	protected Query createQuery(final String queryString,
			final Object... values) {
//		Preconditions.checkNotNull(queryString);
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	protected SQLQuery createSQLQuery(final String queryString,
			final Object... values) {
//		Preconditions.checkNotNull(queryString);
		SQLQuery query = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public SQLQuery createSQLQuery(final String queryString,
			final Map<String, ?> values) {
		SQLQuery query = getSession().createSQLQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	protected final Session getSession() {
		return SessionFactoryUtils.getSession(getSessionFactory(), false);
	}

	protected final void releaseSession(Session session) {
		SessionFactoryUtils.releaseSession(session, getSessionFactory());
	}

	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	public SessionFactory getSessionFactory() {
		log.info("get sessionFactory" + this.sessionFactory);
		return this.sessionFactory;
	}

	@Override
	public void update(String hql) {
		Query query = getSession().createQuery(hql); 
		query.executeUpdate(); 
	}

}
