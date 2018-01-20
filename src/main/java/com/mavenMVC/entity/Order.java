package com.mavenMVC.entity;
// Generated 2017-12-10 21:51:39 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Order generated by hbm2java
 */
@Entity
@Table(name="order"
    ,catalog="farm"
)
public class Order  implements java.io.Serializable {


     private Long orderId;
     private Long userId;
     private Long addressId;
     private String orderMessage;
     private String orderDeliveryId;
     private Integer orderStatus;
     private Integer orderPrice;
     private String orderCommodityIds;
     private Long createTime;
     private Long lastModTime;

    public Order() {
    }

    public Order(Long userId, Long addressId, String orderMessage, String orderDeliveryId, Integer orderStatus, Integer orderPrice, String orderCommodityIds, Long createTime, Long lastModTime) {
       this.userId = userId;
       this.addressId = addressId;
       this.orderMessage = orderMessage;
       this.orderDeliveryId = orderDeliveryId;
       this.orderStatus = orderStatus;
       this.orderPrice = orderPrice;
       this.orderCommodityIds = orderCommodityIds;
       this.createTime = createTime;
       this.lastModTime = lastModTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ORDER_ID", unique=true, nullable=false)
    public Long getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    @Column(name="USER_ID")
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name="ADDRESS_ID")
    public Long getAddressId() {
        return this.addressId;
    }
    
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    
    @Column(name="ORDER_MESSAGE", length=65535)
    public String getOrderMessage() {
        return this.orderMessage;
    }
    
    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }
    
    @Column(name="ORDER_DELIVERY_ID")
    public String getOrderDeliveryId() {
        return this.orderDeliveryId;
    }
    
    public void setOrderDeliveryId(String orderDeliveryId) {
        this.orderDeliveryId = orderDeliveryId;
    }
    
    @Column(name="ORDER_STATUS")
    public Integer getOrderStatus() {
        return this.orderStatus;
    }
    
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    @Column(name="ORDER_PRICE")
    public Integer getOrderPrice() {
        return this.orderPrice;
    }
    
    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }
    
    @Column(name="ORDER_COMMODITY_IDS", length=65535)
    public String getOrderCommodityIds() {
        return this.orderCommodityIds;
    }
    
    public void setOrderCommodityIds(String orderCommodityIds) {
        this.orderCommodityIds = orderCommodityIds;
    }
    
    @Column(name="CREATE_TIME")
    public Long getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="LAST_MOD_TIME")
    public Long getLastModTime() {
        return this.lastModTime;
    }
    
    public void setLastModTime(Long lastModTime) {
        this.lastModTime = lastModTime;
    }


    /**
     * Property name constant for {@code addressId}.
     */
    public static final String PROPERTYNAME_ADDRESS_ID = "addressId";
    /**
     * Property name constant for {@code lastModTime}.
     */
    public static final String PROPERTYNAME_LAST_MOD_TIME = "lastModTime";
    /**
     * Property name constant for {@code orderStatus}.
     */
    public static final String PROPERTYNAME_ORDER_STATUS = "orderStatus";
    /**
     * Property name constant for {@code orderMessage}.
     */
    public static final String PROPERTYNAME_ORDER_MESSAGE = "orderMessage";
    /**
     * Property name constant for {@code orderId}.
     */
    public static final String PROPERTYNAME_ORDER_ID = "orderId";
    /**
     * Property name constant for {@code orderDeliveryId}.
     */
    public static final String PROPERTYNAME_ORDER_DELIVERY_ID = "orderDeliveryId";
    /**
     * Property name constant for {@code orderCommodityIds}.
     */
    public static final String PROPERTYNAME_ORDER_COMMODITY_IDS = "orderCommodityIds";
    /**
     * Property name constant for {@code orderPrice}.
     */
    public static final String PROPERTYNAME_ORDER_PRICE = "orderPrice";
    /**
     * Property name constant for {@code createTime}.
     */
    public static final String PROPERTYNAME_CREATE_TIME = "createTime";
    /**
     * Property name constant for {@code userId}.
     */
    public static final String PROPERTYNAME_USER_ID = "userId";
}


