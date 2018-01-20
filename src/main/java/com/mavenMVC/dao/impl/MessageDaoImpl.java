package com.mavenMVC.dao.impl;

import com.mavenMVC.dao.IMessageDao;
import com.mavenMVC.entity.Message;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lizai on 15/7/23.
 */
@Repository
@Transactional
public class MessageDaoImpl extends GenericDaoHibernate<Message, Long> implements IMessageDao {

    public MessageDaoImpl() {
        super(Message.class);
    }

    @Override
    public void saveOrUpdateMessage(Message message) {
        if (message.getCreateTime() == null) {
            message.setCreateTime(Calendar.getInstance().getTimeInMillis());
        }
        message.setLastModTime(Calendar.getInstance().getTimeInMillis());
        saveOrUpdate(message);
    }

    @Override
    public List<Message> getMessagesByUserIdAndType(Long id, Integer type, Integer start, Integer offset, List<Long> receivedIds) {
        DetachedCriteria query = DetachedCriteria.forClass(Message.class);
        Criterion criterion = Restrictions.or(Restrictions.eq("userId", id),Restrictions.eq("userId", (long)-1));
        Criterion criterion1 = Restrictions.eq("messageType", type);
        query.add(criterion);
        query.add(criterion1);
        if ((receivedIds != null) && (receivedIds.size() > 0)) {
            Criterion criterion3 = Restrictions.not(Restrictions.in("messageId", receivedIds));
            query.add(criterion3);
        }
        query.addOrder(Order.desc("createTime"));
        List<Message> results = new ArrayList();
        if ((start >= 0) && (offset > 0)) {
            results = getHibernateTemplate().findByCriteria(query, start, offset);
        }
        return results;
    }

    @Override
    public List<Message> getMessagesByType(Integer type, Integer start, Integer offset, List<Long> receivedIds) {
        DetachedCriteria query = DetachedCriteria.forClass(Message.class);
        Criterion criterion1 = Restrictions.eq("messageType", type);
        query.add(criterion1);
        if ((receivedIds != null) && (receivedIds.size() > 0)) {
            Criterion criterion3 = Restrictions.not(Restrictions.in("messageId", receivedIds));
            query.add(criterion3);
        }
        query.addOrder(Order.desc("createTime"));
        List<Message> results = new ArrayList();
        if ((start >= 0) && (offset > 0)) {
            results = getHibernateTemplate().findByCriteria(query, start, offset);
        }
        return results;
    }

    @Override
    @Autowired
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
