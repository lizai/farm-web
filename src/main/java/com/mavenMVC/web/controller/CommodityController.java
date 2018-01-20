package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.entity.Commodity;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.ICartCommodityService;
import com.mavenMVC.service.ICommodityService;
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

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(CommodityController.class));

    @Autowired
    private ICommodityService iCommodityService;

    @Autowired
    private ICartCommodityService iCartCommodityService;

    @RequestMapping(value = "/getCommodities", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getCommodities(@CurrentUser Object currentUser,
                          @ApiParam(name = "type", value = "0:生活超市,1:装修超市,2:养殖超市,3:地块")
                          @RequestParam(value = "type") Integer type,
                          @ApiParam(name = "orderType", value = "0:综合,1:销量,2:新品,3:价格,4:分类")
                          @RequestParam(value = "orderType") Integer orderType,
                          @RequestParam(value = "start") Integer start,
                          @RequestParam(value = "offset") Integer offset,
                          @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getCommodities Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Commodity> commodities = iCommodityService.getAllCommodities(type, orderType, start, offset, receivedIds);
            result = JSONArray.fromObject(commodities);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getCommodities Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/searchCommodities", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "根据名称查询商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String searchCommodities(@CurrentUser Object currentUser,
                             @ApiParam(name = "content", value = "搜索框输入内容")
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "start") Integer start,
                             @RequestParam(value = "offset") Integer offset,
                             @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with searchCommodities Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Commodity> commodities = iCommodityService.searchCommoditiesByQuery(content, start, offset, receivedIds);
            result = JSONArray.fromObject(commodities);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done searchCommodities Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getCommoditiyById", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "根据Id商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getCommoditiyById(@CurrentUser Object currentUser,
                             @ApiParam(name = "commodityId", value = "商品id")
                             @RequestParam(value = "commodityId") Long commodityId) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getCommoditiyById Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            Commodity commodities = iCommodityService.getById(commodityId);
            result = JSONArray.fromObject(commodities);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getCommoditiyById Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/addCommoditiyToCart", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "添加商品至购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String addCommoditiyToCart(@CurrentUser Object currentUser,
                               @ApiParam(name = "commodityId", value = "商品id")
                               @RequestParam(value = "commodityId") Long commodityId,
                               @ApiParam(name = "commodityNum", value = "商品个数")
                               @RequestParam(value = "commodityNum") Integer commodityNum) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with addCommoditiyToCart Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            boolean success = iCartCommodityService.addCommodityToCart(userId,commodityId,commodityNum);
            if(!success){
                throw new Exception("添加购物车失败");
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done addCommoditiyToCart Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getCommoditiesFromCart", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取购物车所有商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getCommoditiesFromCart(@CurrentUser Object currentUser,
                                  @RequestParam(value = "start") Integer start,
                                  @RequestParam(value = "offset") Integer offset,
                                  @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getCommoditiesFromCart Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<JSONObject> jsonObjects = iCartCommodityService.getCartDetailByUserId(userId,start,offset,receivedIds);
            result = JSONArray.fromObject(jsonObjects);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getCommoditiesFromCart Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
