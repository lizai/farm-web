package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IChargeDao;
import com.mavenMVC.entity.Charge;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
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
public class ChargeDaoImpl extends GenericDaoHibernate<Charge, Long> implements IChargeDao{

    public ChargeDaoImpl() {
        super(Charge.class);
    }

    @Override
    public Charge getById(Long id) {
        return get(id);
    }

    @Override
    public void saveOrUpdateCharge(Charge charge) {
        if(charge.getCreateTime() == null){
            charge.setCreateTime(Calendar.getInstance().getTimeInMillis());
        }
        charge.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(charge);
    }

    @Override
    public List<Charge> getByUserId(Long userId) {
        DetachedCriteria query = DetachedCriteria.forClass(Charge.class);
        Criterion criterion = Restrictions.eq("userId", userId);
        query.add(criterion);
        List<Charge> charges = getHibernateTemplate().findByCriteria(query);
        if(charges != null){
            return charges;
        }else{
            return null;
        }
    }

    @Override
    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
