package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IQueueService;
import com.mavenMVC.util.RequestManager;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
@RequestMapping("/queue")
public class QueueController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(QueueController.class));

    @Autowired
    private IQueueService iQueueService;

    @RequestMapping(value = "/pushQueue", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "排号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String pushQueue(@CurrentUser Object currentUser,
                          @ApiParam(name = "tableType", value = "通过tableType接口获得")
                          @RequestParam(value = "tableType") Long tableType) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with pushQueue Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            int front = iQueueService.pushQueue(userId,tableType);
            if(front == -1){
                throw new Exception("当前用户有未过期号");
            }
            result.put("frontNum",front);
        } catch (Exception e) {
            logger.error(e.getStackTrace(),e);
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done pushQueue Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getMyQueue", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "查询我的排号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getMyQueue(@CurrentUser Object currentUser) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with getMyQueue Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            result = iQueueService.getMyQueue(userId);
        } catch (Exception e) {
            logger.error(e.getStackTrace(),e);
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getMyQueue Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
