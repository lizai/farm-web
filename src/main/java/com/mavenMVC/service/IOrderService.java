package com.mavenMVC.service;

import com.mavenMVC.entity.Order;

import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
public interface IOrderService {

    public Order getOrderById(Long orderId);

    public List<Order> getOrdersByUserId(Long uid, List<Integer> status, List<Integer> payStatus, Integer orderType, Integer start, Integer offset, List<Long> receivedIds);

    public void saveOrUpdateOrder(Order order);

}
