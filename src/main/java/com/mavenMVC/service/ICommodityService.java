package com.mavenMVC.service;

import com.mavenMVC.entity.Commodity;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
public interface ICommodityService {

    public List<Commodity> getAllCommodities(int commodityType, int orderType, int start, int offset, List<Long> receivedIds);

    public List<Commodity> searchCommoditiesByQuery(String query, int start, int offset, List<Long> receivedIds);

    public boolean saveOrUpdateCommodity(Commodity commodity);

    public Commodity getById(long id);

}
