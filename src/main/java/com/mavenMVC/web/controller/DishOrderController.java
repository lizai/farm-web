package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.dao.ITableTypeDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.entity.Dishorder;
import com.mavenMVC.entity.Tabletype;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IDishService;
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
@RequestMapping("/dishOrder")
public class DishOrderController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(DishOrderController.class));

    @Autowired
    private IDishService iDishService;

    @Autowired
    private IDishOrderDao iDishOrderDao;

    @Autowired
    private IDishDao iDishDao;

    @Autowired
    private ITableTypeDao iTableTypeDao;

    @RequestMapping(value = "/createDishOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "点餐下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String createDishOrder(@CurrentUser Object currentUser,
                           @ApiParam(name = "dishIds", value = "下单菜品id，以,分隔")
                           @RequestParam(value = "dishIds") String dishIds,
                           @ApiParam(name = "price", value = "总金额")
                           @RequestParam(value = "price") Integer price) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with createDinnerOrder Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            Dishorder dishorder = new Dishorder();
            dishorder.setDishIds(dishIds);
            dishorder.setDishOrderPrice(price);
            dishorder.setDishOrderStatus(0);
            dishorder.setUserId(userId);
            iDishOrderDao.saveOrUpdateEntity(dishorder);
            result = JSONObject.fromObject(result);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done createDishOrder Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getMyDishOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取我的点餐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getMyDishOrder(@CurrentUser Object currentUser,
                           @RequestParam(value = "start") Integer start,
                           @RequestParam(value = "offset") Integer offset,
                           @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getMyDishOrder Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Dishorder> dishOrderes = iDishOrderDao.getListByColumn(userId,Dishorder.PROPERTYNAME_USER_ID,0,Integer.MAX_VALUE,null,Dishorder.PROPERTYNAME_CREATE_TIME,true);

            List<Tabletype> tabletypes = iTableTypeDao.getList(Tabletype.PROPERTYNAME_TABLE_TYPE_ID,true,0,Integer.MAX_VALUE,null);
            for(Dishorder dishorder : dishOrderes){
                JSONObject json = JSONObject.fromObject(dishorder);
                if(dishorder.getDishIds()!=null){
                    List<Long> dishIds = new ArrayList<Long>();
                    String idString = dishorder.getDishIds();
                    String[] idArray = idString.split(";");
                    for(String idS : idArray){
                        dishIds.add(Long.parseLong(idS));
                    }
                    List<Dish> dishes = iDishDao.getListInIds(dishIds);
                    JSONArray jsonArray = JSONArray.fromObject(dishes);
                    json.put("dishEntities",jsonArray);
                }
                result.add(json);
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getMyDishOrder Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
