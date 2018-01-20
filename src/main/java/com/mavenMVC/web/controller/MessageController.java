package com.mavenMVC.web.controller;

/**
 * Created by lizai on 15/4/29.
 */

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.entity.Message;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IMessageService;
import com.mavenMVC.util.RequestManager;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
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
 * Created by lizai on 2014/7/25.
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    protected final Logger logger = Logger.getLogger(String.valueOf(MessageController.class));

    @Autowired
    private IMessageService iMessageService;

    @RequestMapping(value = "/getMessages", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取消息内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getMessages(@CurrentUser Object currentUser,
                       @ApiParam(name = "type", value = "0:系统消息,1:平台通知")
                       @RequestParam(value = "type") Integer type,
                       @RequestParam(value = "start") Integer start,
                       @RequestParam(value = "offset") Integer offset,
                       @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getMessages Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Message> messages = iMessageService.getMessagesByUserAndType(userId, type, start, offset, receivedIds);
            result = JSONArray.fromObject(messages);
        } catch (Exception e) {
            logger.error(e.getStackTrace(),e);
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getMessages Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
