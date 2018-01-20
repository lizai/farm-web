package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IOrderDao;
import com.mavenMVC.dao.IUserDao;
import com.mavenMVC.entity.Order;
import com.mavenMVC.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
@Service("OrderServiceImpl")
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IOrderDao orderDao;

    @Override
    public Order getOrderById(Long orderId) {
        return orderDao.getOrderById(orderId);
    }


    @Override
    public List<Order> getOrdersByUserId(Long uid, List<Integer> status, List<Integer> payStatus, Integer orderType, Integer start, Integer offset, List<Long> receivedIds) {
//        if (uid != null && uid > 0) {
//            List<Long> patientIds = patientDao.getPatientIdsByUserId(uid);
//            if (patientIds != null && patientIds.size() > 0) {
//                List<Order> orders = orderDao.getOrdersByPatientIds(patientIds, status, payStatus, orderType, start, offset, receivedIds);
//                if (orders != null && orders.size() > 0) {
//                    return orders;
//                }
//            }
//        }
        return new ArrayList<Order>();
    }


    @Override
    public void saveOrUpdateOrder(Order order) {
        orderDao.saveOrUpdateOrder(order);
    }
}
