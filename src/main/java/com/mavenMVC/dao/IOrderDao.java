package com.mavenMVC.dao;

import com.mavenMVC.entity.Order;

import java.util.List;

/**
 * Created by lizai on 16/4/11.
 */
public interface IOrderDao {

    public Order getOrderById(Long orderId);

    public void saveOrUpdateOrder(Order order);

    public List getList(String orderColumn, boolean ifDesc, int start, int offset, List<Long> receivedIds);

    public List getListLikeColumn(Object content, String column, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc);

    public List getListByColumn(Object content, String column, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc);

    public List getListByAndColumns(List<Object> contents, List<String> columns, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc);

    public List getListByOrColumns(List<Object> contents, List<String> columns, int start, int offset, List<Long> receivedIds, String orderColumn, boolean ifDesc);

}
