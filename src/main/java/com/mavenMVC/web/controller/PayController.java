package com.mavenMVC.web.controller;

import com.mavenMVC.authorization.annotation.CurrentUser;
import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.dao.IOrderDao;
import com.mavenMVC.entity.Dishorder;
import com.mavenMVC.entity.Order;
import com.mavenMVC.entity.User;
import com.mavenMVC.util.HttpRequestUtil;
import com.mavenMVC.util.MD5;
import com.mavenMVC.util.RequestManager;
import com.mavenMVC.util.XmltoJsonUtil;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {

    protected final Logger logger = Logger.getLogger(String.valueOf(PayController.class));

    @Autowired
    private IDishOrderDao iDishOrderDao;

    @Autowired
    private IOrderDao iOrderDao;

    @Autowired
    private HttpServletRequest request;

    private static final String callbak_url = "http://118.190.210.121/pay/notify";

    @RequestMapping(value = "/payOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "支付订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String payOrder(@CurrentUser Object currentUser,
                        @ApiParam(name = "orderId", value = "订单id或餐单id")
                        @RequestParam(value = "orderId") Long orderId,
                        @ApiParam(name = "totalFee", value = "支付金额")
                        @RequestParam(value = "totalFee") Integer totalFee,
                        @ApiParam(name = "ip", value = "客户端ip")
                        @RequestParam(value = "ip") String ip,
                        @ApiParam(name = "orderType", value = "订单类型：0餐单，1超市购物车订单")
                        @RequestParam(value = "orderType") Integer orderType) {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try {
            logger.info("Dealing with payOrder Action...");
            Long userId;
            Assert.notNull(currentUser, "未登录系统");
            if (currentUser instanceof User) {
                userId = ((User) currentUser).getUserId();
            } else {
                throw new Exception("当前用户类型错误");
            }
            String orderBody = "";
            String out_trade_no = "";
            if(orderType == 0){
                orderBody = "牧民家园-餐单支付";
                out_trade_no = "DISHORDER" + orderId + "TIME" + Calendar.getInstance().getTimeInMillis();
            }else if(orderType == 1){
                orderBody = "牧民家园-订单支付";
                out_trade_no = "ORDER" + orderId + "TIME" + Calendar.getInstance().getTimeInMillis();
            }else{
                throw new Exception("订单类型有误");
            }
            SortedMap<Object, Object> paras = new TreeMap<Object, Object>();
            paras.put("appid", "wxbb4e77d5e8574958");
            paras.put("mch_id", "1496083672");
            paras.put("nonce_str", MD5.GetMD5Code(String.valueOf(Math.random())));
            paras.put("body", orderBody);
            paras.put("out_trade_no", out_trade_no);
            paras.put("total_fee", totalFee);
            paras.put("spbill_create_ip", ip);
            paras.put("notify_url",callbak_url);
            paras.put("trade_type","APP");
            String sign =createSign(paras);
            paras.put("sign", sign);
            String xml = getRequestXML(paras);
            String content = HttpRequestUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",xml);
            JSONObject jsonObject = JSONObject.fromObject(XmltoJsonUtil.xml2JSON(content)) ;
            JSONObject result_xml = jsonObject.getJSONObject("xml");
            JSONArray result_code =  result_xml.getJSONArray("result_code");
            String code = (String)result_code.get(0);
            if(code.equalsIgnoreCase("FAIL")){
                logger.info("fail");
                throw new Exception("下单失败");
            }else if(code.equalsIgnoreCase("SUCCESS")){
                logger.info("success");
                SortedMap<Object, Object> reparas = new TreeMap<Object, Object>();
                reparas.put("appid",result_xml.getJSONArray("appid").get(0));
                reparas.put("partnerid",result_xml.getJSONArray("mch_id").get(0));
                reparas.put("prepayid",result_xml.getJSONArray("prepay_id").get(0));
                reparas.put("noncestr",result_xml.getJSONArray("nonce_str").get(0));
                reparas.put("timestamp",Calendar.getInstance().getTimeInMillis()/1000);
                reparas.put("package","Sign=WXPay");
                String newsign = createSign(reparas);
                reparas.put("sign",newsign);
                result = JSONObject.fromObject(reparas);
                logger.info(result.toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done payOrder Action!");
            return requestManager.printJson(result).toString();
        }
    }

    /**
     * 微信订单回调接口
     *
     * @return
     */
    @RequestMapping(value = "/notify", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//    @Authorization
    @ApiOperation(value = "微信支付回调接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
    })
    public
    @ResponseBody
    String payNotify() {
        RequestManager requestManager = new RequestManager();
        JSONObject result = new JSONObject();
        try{
            logger.info("Dealing with payNotify Action...");
            request.setCharacterEncoding("UTF-8");
            InputStream in=request.getInputStream();
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            byte[] buffer =new byte[1024];
            int len=0;
            while((len=in.read(buffer))!=-1){
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            String content=new String(out.toByteArray(),"utf-8");//xml数据

            JSONObject jsonObject = JSONObject.fromObject(XmltoJsonUtil.xml2JSON(content)) ;
            JSONObject result_xml = jsonObject.getJSONObject("xml");
            JSONArray result_code =  result_xml.getJSONArray("result_code");
            String code = (String)result_code.get(0);

            if(code.equalsIgnoreCase("FAIL")){
                throw new Exception("微信统一下单失败");
            }else if(code.equalsIgnoreCase("SUCCESS")){
                JSONArray out_trade_no = result_xml.getJSONArray("out_trade_no");//订单编号
                Map<String,Object> map = new HashMap<String,Object>();
                String orderNo = out_trade_no.get(0).toString().split("TIME")[0];
                Long orderId = (long)0;
                if(orderNo.contains("DISHORDER")){
                    orderId = Long.parseLong(orderNo.split("ORDER")[1]);
                    Dishorder dishorder = iDishOrderDao.getById(orderId);
                    dishorder.setDishOrderStatus(1);
                    iDishOrderDao.saveOrUpdateEntity(dishorder);
                }else{
                    orderId = Long.parseLong(orderNo.split("ORDER")[1]);
                    Order order = iOrderDao.getOrderById(orderId);
                    order.setOrderStatus(1);
                    iOrderDao.saveOrUpdateOrder(order);
                }
            }
        }catch (Exception e) {
            requestManager.putErrorMessage(e.getMessage());
        } finally {
            logger.info("Done payNotify Action!");
            return requestManager.printJson(result).toString();
        }
    }

    //创建md5 数字签证
    public static String createSign(SortedMap<Object, Object> parame) {
        StringBuffer buffer = new StringBuffer();
        Set set = parame.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey().toString() + entry.getValue());
            String key = (String) entry.getKey();
            String value = entry.getValue().toString();
            if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
                buffer.append(key + "=" + value + "&");
            }
        }
        buffer.append("key=" + "muminjiayuan123muminjiayuan123mu");
        String sign = MD5.GetMD5Code(buffer.toString()).toUpperCase();
        System.out.println("签名参数：" + sign);
        return sign;
    }

    //拼接xml 请求路径
    public static String getRequestXML(SortedMap<Object, Object> parame){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>");
        Set set = parame.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String)entry.getKey();
            String value = entry.getValue().toString();
            //过滤相关字段sign
            if("sign".equalsIgnoreCase(key)){
                buffer.append("<"+key+">"+"<![CDATA["+value+"]]>"+"</"+key+">");
            }else{
                buffer.append("<"+key+">"+value+"</"+key+">");
            }
        }
        buffer.append("</xml>");
        return buffer.toString();
    }

    public static void main(String[] args){
        SortedMap<Object, Object> paras = new TreeMap<Object, Object>();
        paras.put("appid", "wxbb4e77d5e8574958");
        paras.put("mch_id", "1496083672");
        paras.put("nonce_str", MD5.GetMD5Code(String.valueOf(Math.random())));
        paras.put("body", "牧民家园-餐单支付");
        paras.put("out_trade_no", "ORDER" + (long)1 + "TIME" + Calendar.getInstance().getTimeInMillis());
        paras.put("total_fee", 1);
        paras.put("spbill_create_ip", "127.0.0.1");
        paras.put("notify_url","http://118.190.210.121/dishOrder/notify");
        paras.put("trade_type","APP");
        String sign =createSign(paras);
        paras.put("sign", sign);
        String xml = getRequestXML(paras);
        String content = HttpRequestUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",xml);
        System.out.println(content);
    }

}
