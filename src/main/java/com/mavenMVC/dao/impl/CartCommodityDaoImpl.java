package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.ICartCommodityDao;
import com.mavenMVC.entity.Cartcommodity;
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
public class CartCommodityDaoImpl extends GenericDaoHibernate<Cartcommodity, Long> implements ICartCommodityDao {

    private static final String identityKey = Cartcommodity.PROPERTYNAME_CART_COMMODITY_ID;

    public CartCommodityDaoImpl() {
        super(Cartcommodity.class);
    }

    @Override
    public Cartcommodity getById(Long id) {
        return get(id);
    }

    @Override
    public void saveOrUpdateEntity(Cartcommodity address) {
        if(address.getCreateTime() == null){
            address.setCreateTime(Calendar.getInstance().getTimeInMillis());
        }
        address.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(address);
    }

    @Override
    public List getList(String orderColumn, boolean ifDesc, int start, int offset, List<Long> receivedIds) {
        DetachedCriteria query = DetachedCriteria.forClass(Cartcommodity.class);
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
        DetachedCriteria query = DetachedCriteria.forClass(Cartcommodity.class);
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
        DetachedCriteria query = DetachedCriteria.forClass(Cartcommodity.class);
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
        DetachedCriteria query = DetachedCriteria.forClass(Cartcommodity.class);
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
        DetachedCriteria query = DetachedCriteria.forClass(Cartcommodity.class);
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
