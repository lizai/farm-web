package com.mavenMVC.service;

import com.mavenMVC.entity.Order;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

/**
 * Created by lizai on 15/6/10.
 */
public interface IPayService {

    public void payOrder(String param);

    public Charge payOrder(Long orderId, Integer money, String channel, String ip);

    public Refund refundOrder(Order order);

//    public Transfer transferOrder(SeeCash seeCash);
    public String transferOrder(Integer amount, String channel, String bankNo,
                              String bankUserName, String openBank, String bankProv, String bankCity,
                              String wxUserOpenId, String wxUserName);
}
