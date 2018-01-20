package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IActivityUserDao;
import com.mavenMVC.entity.Activityuser;
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
 * Created by lizai on 15/6/24.
 */
@Repository
@Transactional
public class ActivityUserDaoImpl extends GenericDaoHibernate<Activityuser, Long> implements IActivityUserDao {

    public ActivityUserDaoImpl() {
        super(Activityuser.class);
    }

    @Override
    public List<Activityuser> getByUserIdAndActivityId(Long userId, Long activityId) {
        DetachedCriteria query = DetachedCriteria.forClass(Activityuser.class);
        if(userId!=null && userId>0){
            Criterion criterion = Restrictions.eq("activityUId", userId);
            query.add(criterion);
        }
        if(activityId!=null && activityId>0){
            Criterion criterion = Restrictions.eq("activityAId", activityId);
            query.add(criterion);
        }
        return findByDetachedCriteria(query);
    }

    @Override
    public List<Activityuser> getByUserIdAndActivityId(Long userId, Long activityId, int start, int offset, List<Long> receivedIds) {
        DetachedCriteria query = DetachedCriteria.forClass(Activityuser.class);
        if(userId!=null && userId>0){
            Criterion criterion = Restrictions.eq("activityUId", userId);
            query.add(criterion);
        }
        if(activityId!=null && activityId>0){
            Criterion criterion = Restrictions.eq("activityAId", activityId);
            query.add(criterion);
        }
        if((receivedIds!=null)&&(receivedIds.size()>0)){
            Criterion criterion1 = Restrictions.not(Restrictions.in("activityUserId", receivedIds));
            query.add(criterion1);
        }
        return findPageByDetachedCriteria(query,start,offset);
    }

    @Override
    public List<Activityuser> getActivitiesByIds(List<Long> ids) {
        DetachedCriteria query = DetachedCriteria.forClass(Activityuser.class);
        Criterion criterion =Restrictions.in("activityUserId", ids);
        query.add(criterion);
        query.addOrder(Order.desc("createTime"));
        return findByDetachedCriteria(query);
    }

    @Override
    public void saveOrUpdateActivityuser(Activityuser activityuser) {
        if(activityuser.getCreateTime() == null){
            activityuser.setCreateTime(Calendar.getInstance().getTimeInMillis());
        }
        activityuser.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(activityuser);
    }

    @Override
    public Activityuser getById(Long id) {
        return get(id);
    }


    @Override
    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

}
