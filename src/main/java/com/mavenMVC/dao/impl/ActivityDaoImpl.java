package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IActivityDao;
import com.mavenMVC.entity.Activity;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lizai on 15/6/24.
 */
@Repository
@Transactional
public class ActivityDaoImpl extends GenericDaoHibernate<Activity, Long> implements IActivityDao {

    public ActivityDaoImpl() {
        super(Activity.class);
    }

    @Override
    public List<Activity> getAllActivities() {
        DetachedCriteria query = DetachedCriteria.forClass(Activity.class);
        Criterion criterion = Restrictions.eq("activityStatus", 0);
        query.add(criterion);
        List<Activity> result = findByDetachedCriteria(query);
        return result;
    }

    @Override
    public List<Activity> getAllActivities(int start, int offset, List<Long> receivedIds) {
        Session session=this.getHibernateTemplate().getSessionFactory().openSession();
        Transaction txn = session.beginTransaction();
        DetachedCriteria query = DetachedCriteria.forClass(Activity.class);
        Criterion criterion = Restrictions.eq("activityStatus", 0);
        query.add(criterion);
        if((receivedIds!=null)&&(receivedIds.size()>0)){
            Criterion criterion1 = Restrictions.not(Restrictions.in("activityId", receivedIds));
            query.add(criterion1);
        }
        query.addOrder(Order.desc("createTime"));
        return findPageByDetachedCriteria(query,start,offset);
    }

    @Override
    public List<Activity> getActivitiesByIds(List<Long> ids) {
        DetachedCriteria query = DetachedCriteria.forClass(Activity.class);
        Criterion criterion =Restrictions.in("activityId", ids);
        query.add(criterion);
        query.addOrder(Order.desc("createTime"));
        if(getHibernateTemplate().findByCriteria(query).size()>0){
            return getHibernateTemplate().findByCriteria(query);
        }else{
            return null;
        }
    }

    @Override
    public void saveOrUpdateActivity(Activity activityEntity) {
        if(activityEntity.getCreateTime() == null){
            activityEntity.setCreateTime(Calendar.getInstance().getTimeInMillis());
        }
        if(activityEntity.getActivityNum() == null){
            activityEntity.setActivityNum(0);
        }
        activityEntity.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(activityEntity);
    }

    @Override
    public int getActivityCount() {
        DetachedCriteria query = DetachedCriteria.forClass(Activity.class);
        query.setProjection(Projections.rowCount())
                .add(Restrictions.eq("activityStatus", 0));
        int totalCount=((Long) this.getHibernateTemplate().findByCriteria(query).get(0)).intValue();
        return totalCount;
    }

    @Override
    public Activity getActivityById(Long id) {
        return get(id);
    }

    @Override
    public List<Activity> getAllActivitiesRegardlessStatus() {
        DetachedCriteria query = DetachedCriteria.forClass(Activity.class);
        return getHibernateTemplate().findByCriteria(query);
    }

    @Override
    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

}
