package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.entity.Book;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IBookService;
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

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
@RequestMapping("/bookDinner")
public class BookDinnerController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(BookDinnerController.class));

    @Autowired
    private IBookService iBookService;

    @RequestMapping(value = "/bookDinner", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "订餐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String bookDinner(@CurrentUser Object currentUser,
                      @ApiParam(name = "dinnerTime", value = "就餐时间,UNIX时间戳")
                      @RequestParam(value = "dinnerTime") Long dinnerTime,
                      @ApiParam(name = "bookNum", value = "就餐人数")
                      @RequestParam(value = "bookNum") Integer bookNum,
                      @ApiParam(name = "tableType", value = "餐桌类型，通过tableType接口获得")
                      @RequestParam(value = "tableType") Long tableType,
                      @ApiParam(name = "bookName", value = "订餐人名称")
                      @RequestParam(value = "bookName") String bookName,
                      @ApiParam(name = "bookCellphone", value = "订餐人手机")
                      @RequestParam(value = "bookCellphone") String bookCellphone,
                      @ApiParam(name = "bookMsg", value = "订餐留言")
                      @RequestParam(value = "bookMsg") String bookMsg) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with bookDinner Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            boolean success = iBookService.bookDinner(userId,dinnerTime,bookNum,tableType,bookName,bookCellphone,bookMsg);
            if(!success){
                throw new Exception("订餐失败");
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done bookDinner Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/getMyBookDinner", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取我的订餐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getMyBookDinner(@CurrentUser Object currentUser) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with getMyBookDinner Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Book> books = iBookService.getMyBooks(userId,0,1,null);
            if(books!=null && books.size()>0){
                result = JSONObject.fromObject(books.get(0));
            }
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getMyBookDinner Action!");
            return requestManager.printJson(result).toString();
        }
    }
}
