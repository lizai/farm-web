package com.mavenMVC.entity;
// Generated 2017-12-10 22:14:56 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
    ,catalog="farm"
)
public class User implements java.io.Serializable {


     private Long userId;
     private String userToken;
     private String userPassword;
     private String userName;
     private String userCellphone;
     private Integer userStatus;
     private String userAddress;
     private String userHeadimage;
     private Integer userMoney;
     private Integer userType;
     private String userWechat;
     private Integer userLogin;
     private Long createTime;
     private Long lastModTime;

    public User() {
    }

    public User(String userToken, String userPassword, String userName, String userCellphone, Integer userStatus, String userAddress, String userHeadimage, Integer userMoney, Integer userType, String userWechat, Long createTime, Long lastModTime) {
       this.userToken = userToken;
       this.userPassword = userPassword;
       this.userName = userName;
       this.userCellphone = userCellphone;
       this.userStatus = userStatus;
       this.userAddress = userAddress;
       this.userHeadimage = userHeadimage;
       this.userMoney = userMoney;
       this.userType = userType;
       this.userWechat = userWechat;
       this.createTime = createTime;
       this.lastModTime = lastModTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="USER_ID", unique=true, nullable=false)
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name="USER_TOKEN", length=45)
    public String getUserToken() {
        return this.userToken;
    }
    
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    
    @Column(name="USER_PASSWORD", length=45)
    public String getUserPassword() {
        return this.userPassword;
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    
    @Column(name="USER_NAME", length=45)
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name="USER_CELLPHONE", length=45)
    public String getUserCellphone() {
        return this.userCellphone;
    }
    
    public void setUserCellphone(String userCellphone) {
        this.userCellphone = userCellphone;
    }
    
    @Column(name="USER_STATUS")
    public Integer getUserStatus() {
        return this.userStatus;
    }
    
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
    
    @Column(name="USER_ADDRESS", length=65535)
    public String getUserAddress() {
        return this.userAddress;
    }
    
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    
    @Column(name="USER_HEADIMAGE")
    public String getUserHeadimage() {
        return this.userHeadimage;
    }
    
    public void setUserHeadimage(String userHeadimage) {
        this.userHeadimage = userHeadimage;
    }
    
    @Column(name="USER_MONEY")
    public Integer getUserMoney() {
        return this.userMoney;
    }
    
    public void setUserMoney(Integer userMoney) {
        this.userMoney = userMoney;
    }
    
    @Column(name="USER_TYPE")
    public Integer getUserType() {
        return this.userType;
    }
    
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    
    @Column(name="USER_LOGIN")
    public Integer getUserLogin() {
        return this.userLogin;
    }
    
    public void setUserLogin(Integer userLogin) {
        this.userLogin = userLogin;
    }

    @Column(name="USER_WECHAT")
    public String getUserWechat() {
        return this.userWechat;
    }

    public void setUserWechat(String userWechat) {
        this.userWechat = userWechat;
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

