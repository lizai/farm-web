package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IUserService;
import com.mavenMVC.service.IVerifyCodeService;
import com.mavenMVC.util.Code;
import com.mavenMVC.util.MD5;
import com.mavenMVC.util.RequestManager;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Calendar;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    protected final Logger logger = Logger.getLogger(String.valueOf(UserController.class));

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IVerifyCodeService iVerifyCodeService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/login", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String login(
            @RequestParam(value = "cellphone", required = false) String cellphone,
            @RequestParam(value = "password", required = false) String password) {
        logger.info("Dealing with login Action...");
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            Assert.notNull(cellphone, "手机号不能为空");
            Assert.notNull(password, "密码不能为空");
            User userEntity = iUserService.loginValid(cellphone, password);
            Assert.notNull(userEntity, "该手机号还未注册或密码有误");
            if(userEntity.getUserToken() == null || userEntity.getUserToken().trim().equals("")){
                String token = MD5.GetMD5Code(cellphone + password + Calendar.getInstance().getTimeInMillis());
                userEntity.setUserToken(token);
                iUserService.updateUser(userEntity);
            }
            result = JSONObject.fromObject(userEntity);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done login Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/logout", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String logout(
            @CurrentUser User currentUser) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with logout Action...");
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser.getUserLogin() == 1) {
                currentUser.setUserLogin(0);
                iUserService.updateUser(currentUser);
            } else {
                throw new Exception("该用户未登录");
            }
            result = JSONObject.fromObject(currentUser);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done logout Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/register", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "新用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String register(
            @RequestParam(value = "cellphone", required = false) String cellphone,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "verifyCode", required = false) Integer verifyCode) {
        logger.info("Dealing with register Action...");
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            Assert.notNull(cellphone, "cellphone can not be empty");
            Assert.notNull(password, "password can not be empty");
            if (iUserService.ifUserCellphoneRegisted(cellphone)) {
                requestManager.putErrorMessage("该手机号已注册");
            } else {
                if(iVerifyCodeService.useVerifyCode(cellphone,verifyCode)){
                    User user = iUserService.registerUser(null, password, cellphone);
                    result = JSONObject.fromObject(user);
                }else{
                    throw new Exception("验证码错误");
                }
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done register Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/resetPassword", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String resetPassword(
            @RequestParam(value = "cellphone", required = false) String cellphone,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "verifyCode", required = false) Integer verifyCode) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with resetPassword Action...");
            Assert.notNull(cellphone, "cellphone can not be empty");
            Assert.notNull(password, "password can not be empty");
            if (iUserService.ifUserCellphoneRegisted(cellphone)) {
                if (iVerifyCodeService.useVerifyCode(cellphone, verifyCode)) {
                    if (iUserService.resetPassword(cellphone, password)) {
                    } else {
                        requestManager.putErrorMessage("修改密码失败");
                    }
                } else {
                    requestManager.putErrorMessage("验证码无效或过期(两分钟)");
                }
            } else {
                requestManager.putErrorMessage("该手机号未注册过，请先完成注册");
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
            result = requestManager.printJson(null);
        } finally {
            logger.info("Done resetPassword Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getUserInfo", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getUserInfo(
            @CurrentUser User currentUser) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with getUserInfo Action...");
            Assert.notNull(currentUser, "未登录系统");
            result = JSONObject.fromObject(currentUser);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getUserInfo Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/updateUserInfo", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
    //    @Authorization
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String updateUserInfo(
            @CurrentUser User currentUser,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "userBirthDay", required = false) String userBirthDay,
            @RequestParam(value = "userSex", required = false) Integer userSex,
            @RequestParam(value = "userCertificateType", required = false) Integer userCertificateType,
            @RequestParam(value = "userCertificateDetail", required = false) String userCertificateDetail) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with updateUserInfo Action...");
            Assert.notNull(currentUser, "未登录系统");
            if ((userName != null) && (!userName.trim().equals(""))) {
                currentUser.setUserName(userName);
            }
//            if ((userBirthDay != null) && (!userBirthDay.trim().equals(""))) {
//                currentUser.setUserBirthDay(userBirthDay);
////                currentUser.setUserAge(userAge);
//            }
//            if (userSex > -1 && userSex < 2) {
//                currentUser.setUserSex(userSex);
//            }
//            if (userCertificateType > 0) {
//                currentUser.setUserCertificateType(userCertificateType);
//            }
//            if ((userCertificateDetail != null) && (!userCertificateDetail.trim().equals(""))) {
//                currentUser.setUserCertificateDetail(userCertificateDetail);
////                currentUser.setUserAge(userAge);
//            }
            iUserService.updateUser(currentUser);
            result = JSONObject.fromObject(currentUser);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done updateUserInfo Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/updateUserCellphone", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
    //    @Authorization
    @ApiOperation(value = "修改用户手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String updateUserCellphone(
            @CurrentUser User currentUser,
            @RequestParam(value = "verifyCode", required = false) Integer verifyCode,
            @RequestParam(value = "cellphone", required = false) String cellphone) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with updateUserCellphone Action...");
//            Assert.notNull(verifyCode,"验证码不能为空");
            Assert.notNull(currentUser, "未登录系统");
            Assert.notNull(cellphone, "新手机号不能为空");
            if (iVerifyCodeService.useVerifyCode(cellphone, verifyCode)) {
                currentUser.setUserCellphone(cellphone);
                iUserService.updateUser(currentUser);
            } else {
                requestManager.putErrorMessage("验证码无效或过期(两分钟)");
            }
            result = JSONObject.fromObject(currentUser);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done updateUserCellphone Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/updateUserHeadImage", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
    //    @Authorization
    @ApiOperation(value = "修改用户头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String updateUserHeadImage(
            @CurrentUser User currentUser,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with updateUserHeadImage Action...");
            Assert.notNull(file, "头像图片不能为空");
            Assert.notNull(currentUser, "未登录系统");
            logger.info(request);
            String path = request.getSession().getServletContext().getRealPath("");
            path = path.substring(0, path.lastIndexOf("/")) + "/files/" + currentUser.getUserToken() + "/";
            logger.info("@@@@ " + path);
            new File(path).mkdir();
            // 得到上传的文件的文件名
            String filename = "headImage_" + Calendar.getInstance().getTimeInMillis() + ".png";
            path += filename;
            file.transferTo(new File(path));
            String fileNameUrl = Code.SERVER_ADDRESS + "/files/" + currentUser.getUserToken() + "/" + filename;
            currentUser.setUserHeadimage(fileNameUrl);
            iUserService.updateUser(currentUser);
            result = JSONObject.fromObject(currentUser);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done updateUserHeadImage Action!");
            return requestManager.printJson(result).toString();
        }
    }

}

