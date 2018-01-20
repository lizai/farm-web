package com.mavenMVC.service.impl;

import com.mavenMVC.dao.ICartCommodityDao;
import com.mavenMVC.dao.ICommodityDao;
import com.mavenMVC.entity.Cartcommodity;
import com.mavenMVC.service.ICartCommodityService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Service("CartCommodityServiceImpl")
@Transactional
public class CartCommodityServiceImpl implements ICartCommodityService {

    @Autowired
    private ICommodityDao commodityDao;

    @Autowired
    private ICartCommodityDao cartCommodityDao;

    @Override
    public boolean addCommodityToCart(Long userId, Long commodityId, int num) {
        if(userId == null || commodityId == null || userId <= 0 || commodityId <=0 || num <= 0){
            return false;
        }
        try{
            Cartcommodity cartcommodity = new Cartcommodity();
            cartcommodity.setUserId(userId);
            cartcommodity.setCommodityId(commodityId);
            cartcommodity.setCommodityNum(num);
            cartCommodityDao.saveOrUpdateEntity(cartcommodity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<JSONObject> getCartDetailByUserId(Long userId, int start, int offset, List<Long> receivedIds) {
        List<Cartcommodity> cartcommodities = cartCommodityDao.getListByColumn(userId, Cartcommodity.PROPERTYNAME_USER_ID, start, offset, receivedIds, Cartcommodity.PROPERTYNAME_CREATE_TIME, true);
        List<JSONObject> result = new ArrayList<JSONObject>();
        for(Cartcommodity cartcommodity : cartcommodities){
            JSONObject json = JSONObject.fromObject(cartcommodity);
            json.put("commodity",commodityDao.getById(cartcommodity.getCommodityId()));
            result.add(json);
        }
        return result;
    }
}