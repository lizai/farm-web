package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.dao.IAddressDao;
import com.mavenMVC.entity.Address;
import com.mavenMVC.entity.User;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(AddressController.class));

    @Autowired
    private IAddressDao iAddressDao;

    @RequestMapping(value = "/getMyAddress", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "获取我的所有地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String getMyAddress(@CurrentUser Object currentUser,
                        @RequestParam(value = "start") Integer start,
                        @RequestParam(value = "offset") Integer offset,
                        @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with getMyAddress Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            List<Address> addresses = iAddressDao.getListByColumn(userId, Address.PROPERTYNAME_USER_ID,start,offset,receivedIds,Address.PROPERTYNAME_ADDRESS_ID,true);
            result = JSONArray.fromObject(addresses);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done getMyAddress Action!");
            return requestManager.printJson(result).toString();
        }
    }

    @RequestMapping(value = "/createAddress", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "创建/修改地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String createAddress(@CurrentUser Object currentUser,
                         @ApiParam(name = "addressId", value = "地址id，新建时不用传")
                         @RequestParam(value = "addressId",required = false) Long addressId,
                         @ApiParam(name = "addressName", value = "收货人姓名")
                         @RequestParam(value = "addressName") String addressName,
                         @ApiParam(name = "addressCellphone", value = "收货人手机")
                         @RequestParam(value = "addressCellphone") String addressCellphone,
                         @ApiParam(name = "addressMsg", value = "收货省市街道")
                         @RequestParam(value = "addressMsg") String addressMsg,
                         @ApiParam(name = "addressDetail", value = "收货具体地址")
                         @RequestParam(value = "addressDetail") String addressDetail,
                         @ApiParam(name = "addressDefault", value = "是否默认地址，1：是，0：否")
                         @RequestParam(value = "addressDefault") Integer addressDefault) {
        RequestManager requestManager = new RequestManager();
        JSONArray result = new JSONArray();
        try {
            logger.info("Dealing with createAddress Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            Address address = null;
            if(addressId!=null && addressId >0){
                address = iAddressDao.getById(addressId);
                if(!address.getUserId().equals(userId)){
                    throw new Exception("地址id和用户id不匹配");
                }
            }else{
                address = new Address();
            }
            address.setAddressCellphone(addressCellphone);
            if(addressDefault == 1){
                List<Object> contents = new ArrayList<Object>();
                contents.add(userId);
                contents.add(1);
                List<String> cols = new ArrayList<String>();
                cols.add(Address.PROPERTYNAME_USER_ID);
                cols.add(Address.PROPERTYNAME_ADDRESS_DEFAULT);
                List<Address> addresses = iAddressDao.getListByAndColumns(contents, cols,0,Integer.MAX_VALUE,null,Address.PROPERTYNAME_ADDRESS_ID,true);
                for(Address add : addresses){
                    add.setAddressDefault(0);
                    iAddressDao.saveOrUpdateEntity(add);
                }
                address.setAddressDefault(addressDefault);
            }else if(addressDefault == 0){
                address.setAddressDefault(addressDefault);
            }else{
                throw new Exception("是否默认参数有误");
            }
            address.setAddressDetail(addressDetail);
            address.setAddressMsg(addressMsg);
            address.setAddressName(addressName);
            address.setUserId(userId);
            iAddressDao.saveOrUpdateEntity(address);
        } catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done createAddress Action!");
            return requestManager.printJson(result).toString();
        }
    }

}
