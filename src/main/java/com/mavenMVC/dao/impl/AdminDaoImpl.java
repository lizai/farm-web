package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IAdminDao;
import com.mavenMVC.entity.Admin;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lizai on 16/4/11.
 */

@Repository
@Transactional
public class AdminDaoImpl extends GenericDaoHibernate<Admin, Long> implements IAdminDao {

    public AdminDaoImpl() {
        super(Admin.class);
    }

    @Override
    public Admin getAdminByName(String name) {
        DetachedCriteria query = DetachedCriteria.forClass(Admin.class);
        Criterion criterion = Restrictions.eq("adminName", name);
        query.add(criterion);
        List<Admin> adminList = getHibernateTemplate().findByCriteria(query);
        if(adminList!=null && adminList.size()>0){
            return adminList.get(0);
        }
        return null;
    }

    @Override
    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
