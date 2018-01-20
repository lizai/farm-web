package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.Authorization;
import com.mavenMVC.dao.IAdminDao;
import com.mavenMVC.entity.Admin;
import com.mavenMVC.service.IUserService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by lizai on 2014/7/25.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    protected final Logger logger = Logger.getLogger(String.valueOf(TestController.class));

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IAdminDao userDao;

    @RequestMapping(value = "/testDB",method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView testDB(HttpServletRequest request, HttpServletResponse response) {
        Admin t1 = userDao.getAdminByName("qianyong");
        logger.info(t1.getAdminName());
        try {
            response.getOutputStream().write(
                    "testtest".getBytes("UTF-8"));
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
        response.setContentType("text/json; charset=UTF-8");
        return null;
    }

    @RequestMapping(value = "/test",method = {RequestMethod.POST, RequestMethod.GET})
    @Authorization
    @ApiOperation(value = "接口测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        logger.info("test interface");
        try {
            response.getOutputStream().write(
                    "接口测试".getBytes("UTF-8"));
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
        response.setContentType("text/json; charset=UTF-8");
        return null;
    }
}
