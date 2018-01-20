package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Service("DishServiceImpl")
@Transactional
public class DishServiceImpl implements IDishService {

    @Autowired
    private IDishDao dishDao;

    @Override
    public List<Dish> getAllDishes(int start, int offset, List<Long> receivedIds) {
        return dishDao.getListByColumn(0,Dish.PROPERTYNAME_DISH_STATUS,start,offset,receivedIds,Dish.PROPERTYNAME_DISH_TYPE,true);
    }
}