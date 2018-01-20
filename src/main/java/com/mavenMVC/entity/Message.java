package com.mavenMVC.entity;
// Generated 2017-12-10 21:51:39 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Message generated by hbm2java
 */
@Entity
@Table(name="message"
    ,catalog="farm"
)
public class Message  implements java.io.Serializable {


     private Long messageId;
     private Long userId;
     private Integer messageType;
     private String messageContent;
     private Long createTime;
     private Long lastModTime;

    public Message() {
    }

    public Message(Long userId, Integer messageType, String messageContent, Long createTime, Long lastModTime) {
       this.userId = userId;
       this.messageType = messageType;
       this.messageContent = messageContent;
       this.createTime = createTime;
       this.lastModTime = lastModTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="MESSAGE_ID", unique=true, nullable=false)
    public Long getMessageId() {
        return this.messageId;
    }
    
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
    
    @Column(name="USER_ID")
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name="MESSAGE_TYPE")
    public Integer getMessageType() {
        return this.messageType;
    }
    
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
    
    @Column(name="MESSAGE_CONTENT", length=65535)
    public String getMessageContent() {
        return this.messageContent;
    }
    
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
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


