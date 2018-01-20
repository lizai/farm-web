package com.mavenMVC.web.controller;

import com.mavenMVC.entity.User;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizai on 15/6/11.
 */
public class BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(BaseController.class));

    //① 获取保存在Session中的用户对象
    protected User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(
                "sessionUser");
    }

    //②将用户对象保存到Session中
    protected void setSessionUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("sessionUser",
                user);
    }

    //③ 获取基于应用程序的url绝对路径
    public final String getAppbaseUrl(HttpServletRequest request, String url) {
        Assert.hasLength(url, "url不能为空");
        Assert.isTrue(url.startsWith("/"), "必须以/打头");
        return request.getContextPath() + url;
    }

//    protected boolean isRequestValid(HttpServletRequest request){
//        try {
//            String udid = RequestUtil.getParameter(request, "udid");
//            String sig = RequestUtil.getParameter(request, "sig");
//            Long time = RequestUtil.getLongPara(request, "time");
//            String compareSig = "";
//            if(udid!=null){
//                compareSig = MD5.GetMD5Code(udid+KEY+time);
//            }else{
//                compareSig = MD5.GetMD5Code(KEY+time);
//            }
//            if(compareSig.trim().equals(sig.trim())){
//                if((Calendar.getInstance().getTimeInMillis()/1000-time)<=300){
//                    return true;
//                }
//            }
//            return false;
//        } catch (Exception e) {
//            logger.error(e);
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    protected boolean isMultipartHttpServletRequestValid(MultipartHttpServletRequest request){
//        try {
//            String udid = request.getParameter("udid");
//            String sig = request.getParameter("sig");
//            Long time = Long.parseLong(request.getParameter("time"));
//            String compareSig = "";
//            if(udid!=null){
//                compareSig = MD5.GetMD5Code(udid+KEY+time);
//            }else{
//                compareSig = MD5.GetMD5Code(KEY+time);
//            }
//            if(compareSig.trim().equals(sig.trim())){
//                if((Calendar.getInstance().getTimeInMillis()/1000-time)<=300){
//                    return true;
//                }
//            }
//            return false;
//        } catch (Exception e) {
//            logger.error(e);
//            e.printStackTrace();
//            return false;
//        }
//    }

}
