package com.mavenMVC.service.impl;

import com.mavenMVC.dao.ICommodityDao;
import com.mavenMVC.entity.Commodity;
import com.mavenMVC.service.ICommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Service("CommodityServiceImpl")
@Transactional
public class CommodityServiceImpl implements ICommodityService {

    @Autowired
    private ICommodityDao commodityDao;

    @Override
    public List<Commodity> getAllCommodities(int commodityType, int orderType, int start, int offset, List<Long> receivedIds) {
        if(commodityType > 3 || commodityType < 0 || orderType > 4 || orderType < 0){
            return null;
        }
        List<Commodity> commodities = new ArrayList<Commodity>();
        if (orderType == 0) {
            commodities = commodityDao.getListByColumn(commodityType, Commodity.PROPERTYNAME_COMMODITY_TYPE, start, offset, receivedIds, Commodity.PROPERTYNAME_COMMODITY_ID, true);
        } else if (orderType == 1) {
            commodities = commodityDao.getListByColumn(commodityType, Commodity.PROPERTYNAME_COMMODITY_TYPE, start, offset, receivedIds, Commodity.PROPERTYNAME_COMMODITY_SALE_NUM, true);
        } else if (orderType == 2) {
            commodities = commodityDao.getListByColumn(commodityType, Commodity.PROPERTYNAME_COMMODITY_TYPE, start, offset, receivedIds, Commodity.PROPERTYNAME_CREATE_TIME, true);
        } else if (orderType == 3) {
            commodities = commodityDao.getListByColumn(commodityType, Commodity.PROPERTYNAME_COMMODITY_TYPE, start, offset, receivedIds, Commodity.PROPERTYNAME_COMMODITY_PRICE, true);
        } else if (orderType == 4) {
            commodities = commodityDao.getListByColumn(commodityType, Commodity.PROPERTYNAME_COMMODITY_TYPE, start, offset, receivedIds, Commodity.PROPERTYNAME_COMMODITY_CATEGORY, true);
        }
        return commodities;
    }

    @Override
    public List<Commodity> searchCommoditiesByQuery(String query, int start, int offset, List<Long> receivedIds) {
        return commodityDao.getListLikeColumn(query, Commodity.PROPERTYNAME_COMMODITY_NAME, start, offset, receivedIds, Commodity.PROPERTYNAME_CREATE_TIME, true);
    }

    @Override
    public boolean saveOrUpdateCommodity(Commodity commodity) {
        try{
            commodityDao.saveOrUpdateEntity(commodity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Commodity getById(long id) {
        return commodityDao.getById(id);
    }
}