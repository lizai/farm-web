package com.mavenMVC.entity;
// Generated 2017-12-10 21:51:39 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Charge generated by hbm2java
 */
@Entity
@Table(name="charge"
    ,catalog="farm"
)
public class Charge  implements java.io.Serializable {


     private Long chargeId;
     private Long userId;
     private Integer chargePrice;
     private Integer chargeStatus;
     private Long createTime;
     private Long lastModTime;

    public Charge() {
    }

    public Charge(Long userId, Integer chargePrice, Integer chargeStatus, Long createTime, Long lastModTime) {
       this.userId = userId;
       this.chargePrice = chargePrice;
       this.chargeStatus = chargeStatus;
       this.createTime = createTime;
       this.lastModTime = lastModTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="CHARGE_ID", unique=true, nullable=false)
    public Long getChargeId() {
        return this.chargeId;
    }
    
    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }
    
    @Column(name="USER_ID")
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name="CHARGE_PRICE")
    public Integer getChargePrice() {
        return this.chargePrice;
    }
    
    public void setChargePrice(Integer chargePrice) {
        this.chargePrice = chargePrice;
    }
    
    @Column(name="CHARGE_STATUS")
    public Integer getChargeStatus() {
        return this.chargeStatus;
    }
    
    public void setChargeStatus(Integer chargeStatus) {
        this.chargeStatus = chargeStatus;
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




}


