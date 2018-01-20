package com.mavenMVC.service;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
public interface ICartCommodityService {

    public boolean addCommodityToCart(Long userId, Long commodityId, int num);

    public List<JSONObject> getCartDetailByUserId(Long userId, int start, int offset, List<Long> receivedIds);
}
