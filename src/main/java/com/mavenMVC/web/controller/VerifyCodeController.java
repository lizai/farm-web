package com.mavenMVC.web.controller;

import com.mavenMVC.entity.Verifycode;
import com.mavenMVC.service.IVerifyCodeService;
import com.mavenMVC.util.RequestManager;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizai on 15/6/11.
 */

@Controller
@RequestMapping(value = "/verifyCode")
public class VerifyCodeController {

    protected final Logger logger = Logger.getLogger(String.valueOf(VerifyCodeController.class));

    @Autowired
    private IVerifyCodeService iVerifyCodeService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/get", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    String getVerifyCode(@RequestParam(value = "cellphone") String cellphone) {
        RequestManager requestManager = new RequestManager();
        JSONObject jo = new JSONObject();
        try {
            logger.info("Dealing with getVerifyCode Action...");
            if (cellphone != null) {
                Verifycode verifycode = iVerifyCodeService.getVerifyCode(cellphone);
                jo = JSONObject.fromObject(verifycode);
            } else {
                requestManager.putErrorMessage("没有手机号");
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            logger.info("Done getVerifyCode Action!");
            return requestManager.printJson(jo).toString();
        }
    }

    public static void main(String[] args) {
        VerifyCodeController a = new VerifyCodeController();
        a.getVerifyCode("15120073762");
    }

}
