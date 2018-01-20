package com.mavenMVC.entity;
// Generated 2017-12-10 21:51:39 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Book generated by hbm2java
 */
@Entity
@Table(name="book"
    ,catalog="farm"
)
public class Book  implements java.io.Serializable {


     private Long bookId;
     private Long userId;
     private Integer bookNum;
     private Long bookTableType;
     private String bookName;
     private String bookCellphone;
     private String bookMsg;
     private Long dinnerTime;
     private Long createTime;
     private Long lastModTime;

    public Book() {
    }

    public Book(Long userId, Integer bookNum, Long bookTableType, String bookName, String bookCellphone, String bookMsg, Long dinnerTime, Long createTime, Long lastModTime) {
       this.userId = userId;
       this.bookNum = bookNum;
       this.bookTableType = bookTableType;
       this.bookName = bookName;
       this.bookCellphone = bookCellphone;
       this.bookMsg = bookMsg;
       this.dinnerTime = dinnerTime;
       this.createTime = createTime;
       this.lastModTime = lastModTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="BOOK_ID", unique=true, nullable=false)
    public Long getBookId() {
        return this.bookId;
    }
    
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    
    @Column(name="USER_ID")
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name="BOOK_NUM")
    public Integer getBookNum() {
        return this.bookNum;
    }
    
    public void setBookNum(Integer bookNum) {
        this.bookNum = bookNum;
    }
    
    @Column(name="BOOK_TABLE_TYPE")
    public Long getBookTableType() {
        return this.bookTableType;
    }
    
    public void setBookTableType(Long bookTableType) {
        this.bookTableType = bookTableType;
    }
    
    @Column(name="BOOK_NAME", length=45)
    public String getBookName() {
        return this.bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    @Column(name="BOOK_CELLPHONE", length=45)
    public String getBookCellphone() {
        return this.bookCellphone;
    }
    
    public void setBookCellphone(String bookCellphone) {
        this.bookCellphone = bookCellphone;
    }
    
    @Column(name="BOOK_MSG", length=65535)
    public String getBookMsg() {
        return this.bookMsg;
    }
    
    public void setBookMsg(String bookMsg) {
        this.bookMsg = bookMsg;
    }

    @Column(name="DINNER_TIME")
    public Long getDinnerTime() {
        return dinnerTime;
    }

    public void setDinnerTime(Long dinnerTime) {
        this.dinnerTime = dinnerTime;
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
     * Property name constant for {@code lastModTime}.
     */
    public static final String PROPERTYNAME_LAST_MOD_TIME = "lastModTime";
    /**
     * Property name constant for {@code createTime}.
     */
    public static final String PROPERTYNAME_CREATE_TIME = "createTime";
    /**
     * Property name constant for {@code bookId}.
     */
    public static final String PROPERTYNAME_BOOK_ID = "bookId";
    /**
     * Property name constant for {@code bookName}.
     */
    public static final String PROPERTYNAME_BOOK_NAME = "bookName";
    /**
     * Property name constant for {@code bookMsg}.
     */
    public static final String PROPERTYNAME_BOOK_MSG = "bookMsg";
    /**
     * Property name constant for {@code bookNum}.
     */
    public static final String PROPERTYNAME_BOOK_NUM = "bookNum";
    /**
     * Property name constant for {@code bookCellphone}.
     */
    public static final String PROPERTYNAME_BOOK_CELLPHONE = "bookCellphone";
    /**
     * Property name constant for {@code bookTableType}.
     */
    public static final String PROPERTYNAME_BOOK_TABLE_TYPE = "bookTableType";
    /**
     * Property name constant for {@code userId}.
     */
    public static final String PROPERTYNAME_USER_ID = "userId";
    /**
     * Property name constant for {@code dinnerTime}.
     */
    public static final String PROPERTYNAME_DINNER_TIME = "dinnerTime";
}


