package com.mavenMVC.dao;

import com.mavenMVC.entity.Charge;

import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
public interface IChargeDao {

    public Charge getById(Long id);

    public void saveOrUpdateCharge(Charge charge);

    public List<Charge> getByUserId(Long userId);

}
