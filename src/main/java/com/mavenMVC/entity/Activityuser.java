package com.mavenMVC.entity;
// Generated 2017-12-10 21:51:39 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Activityuser generated by hbm2java
 */
@Entity
@Table(name="activityuser"
    ,catalog="farm"
)
public class Activityuser  implements java.io.Serializable {


     private Long activityUserId;
     private Long activityUId;
     private Long activityAId;
     private String activityUserName;
     private String activityUserCellphone;
     private Integer activityUserStatus;
     private Long createTime;
     private Long lastModTime;

    public Activityuser() {
    }

    public Activityuser(Long activityUId, Long activityAId, String activityUserName, String activityUserCellphone, Integer activityUserStatus, Long createTime, Long lastModTime) {
        this.activityUId = activityUId;
        this.activityAId = activityAId;
        this.activityUserName = activityUserName;
       this.activityUserCellphone = activityUserCellphone;
       this.activityUserStatus = activityUserStatus;
       this.createTime = createTime;
       this.lastModTime = lastModTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ACTIVITY_USER_ID", unique=true, nullable=false)
    public Long getActivityUserId() {
        return this.activityUserId;
    }
    
    public void setActivityUserId(Long activityUserId) {
        this.activityUserId = activityUserId;
    }

    @Column(name="ACTIVITY_ID")
    public Long getActivityUId() {
        return activityUId;
    }

    public void setActivityUId(Long activityUId) {
        this.activityUId = activityUId;
    }

    @Column(name="ACTIVITY_AID")
    public Long getActivityAId() {
        return activityAId;
    }

    public void setActivityAId(Long activityAId) {
        this.activityAId = activityAId;
    }

    @Column(name="ACTIVITY_USER_NAME", length=45)
    public String getActivityUserName() {
        return this.activityUserName;
    }
    
    public void setActivityUserName(String activityUserName) {
        this.activityUserName = activityUserName;
    }
    
    @Column(name="ACTIVITY_USER_CELLPHONE", length=45)
    public String getActivityUserCellphone() {
        return this.activityUserCellphone;
    }
    
    public void setActivityUserCellphone(String activityUserCellphone) {
        this.activityUserCellphone = activityUserCellphone;
    }
    
    @Column(name="ACTIVITY_USER_STATUS")
    public Integer getActivityUserStatus() {
        return this.activityUserStatus;
    }
    
    public void setActivityUserStatus(Integer activityUserStatus) {
        this.activityUserStatus = activityUserStatus;
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

