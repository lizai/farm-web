package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.entity.Activity;
import com.mavenMVC.entity.Activityuser;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IActivityService;
import com.mavenMVC.service.IUserService;
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
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(ActivityController.class));

    @Autowired
    private IActivityService iActivityService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/getActivities", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取活动内容列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getActivities(@CurrentUser Object currentUser,
                       @RequestParam(value = "start") Integer start,
                       @RequestParam(value = "offset") Integer offset,
                       @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getActivities Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Activity> activities = iActivityService.getActivities(start,offset,receivedIds);
            result = JSONArray.fromObject(activities);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getActivities Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getMyActivities", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取我的活动内容列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getMyActivities(@CurrentUser Object currentUser,
                         @RequestParam(value = "start") Integer start,
                         @RequestParam(value = "offset") Integer offset,
                         @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getMyActivities Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Activity> activities = iActivityService.getActivitiesByUserId(userId, start,offset,receivedIds);
            result = JSONArray.fromObject(activities);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getMyActivities Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/joinActivity", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "参加活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String joinActivity(@CurrentUser Object currentUser,
                           @RequestParam(value = "activityId") Long activityId,
                           @RequestParam(value = "userName") String userName,
                           @RequestParam(value = "userCellphone") String userCellphone) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with joinActivity Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            Activityuser activityuser = new Activityuser();
            activityuser.setActivityAId(activityId);
            activityuser.setActivityUId(userId);
            activityuser.setActivityUserCellphone(userCellphone);
            activityuser.setActivityUserName(userName);
            boolean success = iActivityService.takePartInActivity(activityuser);
            if(!success){
                throw new Exception("报名失败");
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done joinActivity Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
