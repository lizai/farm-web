package com.mavenMVC.entity;
// Generated 2017-12-10 21:51:39 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Dish generated by hbm2java
 */
@Entity
@Table(name="dish"
    ,catalog="farm"
)
public class Dish  implements java.io.Serializable {


     private Long dishId;
     private String dishName;
     private Integer dishPrice;
     private String dishImage;
     private Integer dishStatus;
     private Long dishType;
     private Long createTime;
     private Long lastModTime;

    public Dish() {
    }

    public Dish(String dishName, Integer dishPrice, String dishImage, Integer dishStatus, Long dishType, Long createTime, Long lastModTime) {
       this.dishName = dishName;
       this.dishPrice = dishPrice;
       this.dishImage = dishImage;
       this.dishStatus = dishStatus;
       this.dishType = dishType;
       this.createTime = createTime;
       this.lastModTime = lastModTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="DISH_ID", unique=true, nullable=false)
    public Long getDishId() {
        return this.dishId;
    }
    
    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }
    
    @Column(name="DISH_NAME", length=45)
    public String getDishName() {
        return this.dishName;
    }
    
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
    
    @Column(name="DISH_PRICE")
    public Integer getDishPrice() {
        return this.dishPrice;
    }
    
    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }
    
    @Column(name="DISH_IMAGE")
    public String getDishImage() {
        return this.dishImage;
    }
    
    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }
    
    @Column(name="DISH_STATUS")
    public Integer getDishStatus() {
        return this.dishStatus;
    }
    
    public void setDishStatus(Integer dishStatus) {
        this.dishStatus = dishStatus;
    }
    
    @Column(name="DISH_TYPE")
    public Long getDishType() {
        return this.dishType;
    }
    
    public void setDishType(Long dishType) {
        this.dishType = dishType;
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
     * Property name constant for {@code dishPrice}.
     */
    public static final String PROPERTYNAME_DISH_PRICE = "dishPrice";
    /**
     * Property name constant for {@code lastModTime}.
     */
    public static final String PROPERTYNAME_LAST_MOD_TIME = "lastModTime";
    /**
     * Property name constant for {@code dishStatus}.
     */
    public static final String PROPERTYNAME_DISH_STATUS = "dishStatus";
    /**
     * Property name constant for {@code dishType}.
     */
    public static final String PROPERTYNAME_DISH_TYPE = "dishType";
    /**
     * Property name constant for {@code dishImage}.
     */
    public static final String PROPERTYNAME_DISH_IMAGE = "dishImage";
    /**
     * Property name constant for {@code createTime}.
     */
    public static final String PROPERTYNAME_CREATE_TIME = "createTime";
    /**
     * Property name constant for {@code dishName}.
     */
    public static final String PROPERTYNAME_DISH_NAME = "dishName";
    /**
     * Property name constant for {@code dishId}.
     */
    public static final String PROPERTYNAME_DISH_ID = "dishId";
}


