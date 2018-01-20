package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IVerifyCodeDao;
import com.mavenMVC.entity.Verifycode;
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
public class VerifyCodeDaoImpl extends GenericDaoHibernate<Verifycode, Long> implements IVerifyCodeDao{

    public VerifyCodeDaoImpl() {
        super(Verifycode.class);
    }

    @Override
    public void saveVerifyCode(Verifycode verifycode) {
        verifycode.setCreateTime(Calendar.getInstance().getTimeInMillis());
        verifycode.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(verifycode);
    }

    @Override
    public Verifycode getLastByCellphone(String cellphone) {
        DetachedCriteria query = DetachedCriteria.forClass(Verifycode.class);
        if (cellphone != null && !cellphone.trim().equals("")) {
            Criterion criterion = Restrictions.eq("verifycodeCellpyone", cellphone);
            query.add(criterion);
            query.addOrder(org.hibernate.criterion.Order.desc("createTime"));
            List<Verifycode> results = getHibernateTemplate().findByCriteria(query, 0, 1);
            if(results != null && results.size() > 0){
                return results.get(0);
            }
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
