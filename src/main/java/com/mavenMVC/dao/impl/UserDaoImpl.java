package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IUserDao;
import com.mavenMVC.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */

@Repository
@Transactional
public class UserDaoImpl extends GenericDaoHibernate<User, Long> implements IUserDao{

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User getById(Long id) {
        return get(id);
    }

    @Override
    public User getUserByName(String name) {
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        Criterion criterion = Restrictions.eq("userName", name);
        query.add(criterion);
        if(getHibernateTemplate().findByCriteria(query).size()>0){
            return (User) getHibernateTemplate().findByCriteria(query).get(0);
        }else{
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        user.setCreateTime(Calendar.getInstance().getTimeInMillis());
        user.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(user);
    }

    @Override
    public void updateUser(User user) {
        user.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(getHibernateTemplate().merge(user));
        getHibernateTemplate().merge(user);
    }

    @Override
    public User getUserByToken(String token) {
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        Criterion criterion = Restrictions.eq("userToken", token);
        query.add(criterion);
        if(getHibernateTemplate().findByCriteria(query).size()>0){
            return (User) getHibernateTemplate().findByCriteria(query).get(0);
        }else{
            return null;
        }
    }

    @Override
    public boolean ifUserExists(String cellphone) {
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        Criterion criterion = Restrictions.eq("userCellphone", cellphone);
        query.add(criterion);
        if(getHibernateTemplate().findByCriteria(query).size()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User getUserByCellphone(String cellphone) {
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        Criterion criterion = Restrictions.eq("userCellphone", cellphone);
        query.add(criterion);
        if(getHibernateTemplate().findByCriteria(query).size()>0){
            return (User) getHibernateTemplate().findByCriteria(query).get(0);
        }else{
            return null;
        }
    }

    @Override
    public User getUserByCellphoneAndPassword(String cellphone, String password) {
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        Criterion criterion = Restrictions.eq("userCellphone", cellphone);
        Criterion criterion1 = Restrictions.eq("userPassword", password);
        query.add(criterion);
        query.add(criterion1);
        if(getHibernateTemplate().findByCriteria(query).size()>0){
            return (User) getHibernateTemplate().findByCriteria(query).get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<User> getUsersByIds(List<Long> ids) {
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        Criterion criterion = Restrictions.in("userId", ids);
        query.add(criterion);
        return getHibernateTemplate().findByCriteria(query);
    }

    @Override
    public List<User> getAllUsers() {
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.addOrder(Order.desc("createTime"));
        return getHibernateTemplate().findByCriteria(query);
    }

    @Override
    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
