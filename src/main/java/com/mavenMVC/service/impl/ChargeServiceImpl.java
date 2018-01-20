package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IChargeDao;
import com.mavenMVC.entity.Charge;
import com.mavenMVC.service.IChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
@Service("ChargeServiceImpl")
@Transactional
public class ChargeServiceImpl implements IChargeService{

    @Autowired
    private IChargeDao chargeDao;

    @Override
    public List<Charge> getChargesByUserId(Long userId) {
        return chargeDao.getByUserId(userId);
    }

    @Override
    public void createCharge(Charge charge) {
        chargeDao.saveOrUpdateCharge(charge);
    }
}
