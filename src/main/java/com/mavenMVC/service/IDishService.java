package com.mavenMVC.service;

import com.mavenMVC.entity.Dish;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
public interface IDishService {

    public List<Dish> getAllDishes(int start, int offset, List<Long> receivedIds);

}
