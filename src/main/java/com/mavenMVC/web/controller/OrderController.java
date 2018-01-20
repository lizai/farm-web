package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.dao.IAddressDao;
import com.mavenMVC.dao.ICommodityDao;
import com.mavenMVC.dao.IOrderDao;
import com.mavenMVC.entity.*;
import com.mavenMVC.service.IOrderService;
import com.mavenMVC.util.RequestManager;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(OrderController.class));

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderDao iOrderDao;

    @Autowired
    private ICommodityDao iCommodityDao;

    @Autowired
    private IAddressDao iAddressDao;

    @RequestMapping(value = "/createOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String createOrder(@CurrentUser Object currentUser,
                           @ApiParam(name = "commodityIds", value = "商品id，以,分隔")
                           @RequestParam(value = "commodityIds") String commodityIds,
                           @ApiParam(name = "addressId", value = "地址id")
                           @RequestParam(value = "addressId") Long addressId,
                           @ApiParam(name = "price", value = "总金额")
                           @RequestParam(value = "price") Integer price,
                           @ApiParam(name = "deliveryId", value = "快递单号")
                           @RequestParam(value = "deliveryId") String deliveryId,
                           @ApiParam(name = "orderMsg", value = "订单留言")
                           @RequestParam(value = "orderMsg") String orderMsg) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with createOrder Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            Order order = new Order();
            order.setAddressId(addressId);
            order.setOrderStatus(0);
            order.setOrderCommodityIds(commodityIds);
            order.setOrderDeliveryId(deliveryId);
            order.setOrderMessage(orderMsg);
            order.setUserId(userId);
            order.setOrderPrice(price);
            iOrderDao.saveOrUpdateOrder(order);
            result = JSONObject.fromObject(result);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done createOrder Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getMyOrders", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取我的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getMyOrders(@CurrentUser Object currentUser,
                           @RequestParam(value = "start") Integer start,
                           @RequestParam(value = "offset") Integer offset,
                           @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getMyOrders Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Order> orders = iOrderDao.getListByColumn(userId,Dishorder.PROPERTYNAME_USER_ID,0,Integer.MAX_VALUE,null,Dishorder.PROPERTYNAME_CREATE_TIME,true);
            for(Order order : orders){
                JSONObject json = JSONObject.fromObject(order);
                if(order.getAddressId()!=null){
                    Address address = iAddressDao.getById(order.getAddressId());
                    json.put("addressEntity",JSONObject.fromObject(address));
                }
                if(order.getOrderCommodityIds()!=null){
                    List<Long> commodityIds = new ArrayList<Long>();
                    String idString = order.getOrderCommodityIds();
                    String[] idArray = idString.split(";");
                    for(String idS : idArray){
                        commodityIds.add(Long.parseLong(idS));
                    }
                    List<Commodity> commodities = iCommodityDao.getListInIds(commodityIds);
                    JSONArray jsonArray = JSONArray.fromObject(commodities);
                    json.put("commodityEntities",jsonArray);
                }
                result.add(json);
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getMyOrders Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
