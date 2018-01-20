package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.dao.ICommodityCategoryDao;
import com.mavenMVC.entity.Commoditycategory;
import com.mavenMVC.entity.User;
import com.mavenMVC.util.RequestManager;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
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
@RequestMapping("/commodityCategory")
public class CommodityCategoryController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(CommodityCategoryController.class));

    @Autowired
    private ICommodityCategoryDao iCommodityCategoryDao;

    @RequestMapping(value = "/getAllCommodityCategories", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取所有商品品类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getAllCommodityCategories(@CurrentUser Object currentUser,
                        @RequestParam(value = "start") Integer start,
                        @RequestParam(value = "offset") Integer offset,
                        @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getAllCommodityCategories Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Commoditycategory> categories = iCommodityCategoryDao.getList(Commoditycategory.PROPERTYNAME_COMMODITY_CATEGORY_ID,false,start,offset,receivedIds);
            result = JSONArray.fromObject(categories);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getAllCommodityCategories Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
