package com.mavenMVC.service;

import com.mavenMVC.entity.Charge;

import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
public interface IChargeService {

    public List<Charge> getChargesByUserId(Long userId);

    public void createCharge(Charge charge);

}
