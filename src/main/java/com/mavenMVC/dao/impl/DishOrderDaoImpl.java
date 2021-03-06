package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.entity.Dishorder;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
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
public class DishOrderDaoImpl extends GenericDaoHibernate<Dishorder, Long> implements IDishOrderDao {

    private static final String identityKey = Dishorder.PROPERTYNAME_DISH_ORDER_ID;

    public DishOrderDaoImpl() {
        super(Dishorder.class);
    }

    @Override
    public Dishorder getById(Long id) {
        return get(id);
    }

    @Override
    public void saveOrUpdateEntity(Dishorder book) {
        if(book.getCreateTime() == null){
            book.setCreateTime(Calendar.getInstance().getTimeInMillis());
        }
        book.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(book);
    }

    @Override
    public List getList(String orderColumn, boolean ifDesc, int start, int offset, List<Long> receivedIds) {
        DetachedCriteria query = DetachedCriteria.forClass(Dishorder.class);
        if(orderColumn != null && !orderColumn.trim().equals("")){
            query.addOrder(ifDesc ? Order.desc(orderColumn) : Order.asc(orderColumn));
        }
        if ((receivedIds != null) && (receivedIds.size() > 0)) {
            Criterion criterion1 = Restrictions.not(Restrictions.in(identityKey, receivedIds));
            query.add(criterion1);
        }
        return findPageByDetachedCriteria(query,start,offset);
    }

    @Override
    public List getListLikeColumn(Object content, String column, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc) {
        DetachedCriteria query = DetachedCriteria.forClass(Dishorder.class);
        Criterion criterion = Restrictions.like(column, content);
        query.add(criterion);
        if ((receivedIds != null) && (receivedIds.size() > 0)) {
            Criterion criterion1 = Restrictions.not(Restrictions.in(identityKey, receivedIds));
            query.add(criterion1);
        }
        if(orderColumn != null && !orderColumn.trim().equals("")){
            query.addOrder(ifDesc ? Order.desc(orderColumn) : Order.asc(orderColumn));
        }
        return findPageByDetachedCriteria(query, start, offset);
    }

    @Override
    public List getListByColumn(Object content, String column, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc) {
        DetachedCriteria query = DetachedCriteria.forClass(Dishorder.class);
        Criterion criterion = Restrictions.eq(column, content);
        query.add(criterion);
        if ((receivedIds != null) && (receivedIds.size() > 0)) {
            Criterion criterion1 = Restrictions.not(Restrictions.in(identityKey, receivedIds));
            query.add(criterion1);
        }
        if(orderColumn != null && !orderColumn.trim().equals("")){
            query.addOrder(ifDesc ? Order.desc(orderColumn) : Order.asc(orderColumn));
        }
        return findPageByDetachedCriteria(query, start, offset);
    }

    @Override
    public List getListByAndColumns(List<Object> contents, List<String> columns, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc) {
        DetachedCriteria query = DetachedCriteria.forClass(Dishorder.class);
        if (contents == null || columns == null || columns.size() != contents.size()) {
            return null;
        }
        Conjunction cnj = Restrictions.conjunction();
        for (int i = 0; i < columns.size(); i++){
            cnj.add(Restrictions.eq(columns.get(i),contents.get(i)));
        }
        query.add(cnj);
        if ((receivedIds != null) && (receivedIds.size() > 0)) {
            Criterion criterion1 = Restrictions.not(Restrictions.in(identityKey, receivedIds));
            query.add(criterion1);
        }
        if(orderColumn != null && !orderColumn.trim().equals("")){
            query.addOrder(ifDesc ? Order.desc(orderColumn) : Order.asc(orderColumn));
        }
        return findPageByDetachedCriteria(query, start, offset);
    }

    @Override
    public List getListByOrColumns(List<Object> contents, List<String> columns, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc) {
        DetachedCriteria query = DetachedCriteria.forClass(Dishorder.class);
        if (contents == null || columns == null || columns.size() != contents.size()) {
            return null;
        }
        Disjunction dis = Restrictions.disjunction();
        for (int i = 0; i < columns.size(); i++){
            dis.add(Restrictions.eq(columns.get(i),contents.get(i)));
        }
        query.add(dis);
        if ((receivedIds != null) && (receivedIds.size() > 0)) {
            Criterion criterion1 = Restrictions.not(Restrictions.in(identityKey, receivedIds));
            query.add(criterion1);
        }
        if(orderColumn != null && !orderColumn.trim().equals("")){
            query.addOrder(ifDesc ? Order.desc(orderColumn) : Order.asc(orderColumn));
        }
        return findPageByDetachedCriteria(query, start, offset);
    }


    @Override
    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
