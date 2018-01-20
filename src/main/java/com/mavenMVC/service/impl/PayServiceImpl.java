package com.mavenMVC.service.impl;

import com.mavenMVC.entity.Order;
import com.mavenMVC.service.IOrderService;
import com.mavenMVC.service.IPayService;
import com.mavenMVC.util.Code;
import com.mavenMVC.util.HttpRequestUtil;
import com.mavenMVC.util.PayCode;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizai on 16/5/4.
 */

@Service
@Transactional
public class PayServiceImpl implements IPayService {

    @Autowired
    private IOrderService orderService;

//    @Autowired
//    private IDoctorService doctorService;
//
//    @Autowired
//    private ISeeCashDao seeCashDao;

    @Override
    public void payOrder(String param) {
        HttpRequestUtil.sendGet("https://api.mch.weixin.qq.com/pay/unifiedorder",param);
    }

    @Override
    public Charge payOrder(Long orderId, Integer money, String channel, String ip) {
        Pingpp.apiKey = Code.PING_CODE;
        Pingpp.privateKey = Code.PING_PRIVATE_KEY;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("order_no", orderId);
        chargeParams.put("amount", money);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", Code.PING_APP_CODE);
        chargeParams.put("app", app);
        chargeParams.put("channel", channel);
        chargeParams.put("currency", "cny");
        chargeParams.put("client_ip", ip);
        chargeParams.put("subject", "视频问诊服务费");
        chargeParams.put("body", "视频问诊服务费");
        try {
            Charge charge = Charge.create(chargeParams);
            charge.getId();
            return charge;
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Refund refundOrder(Order order) {
        return null;
    }

//    @Override
//    public Refund refundOrder(Order order) {
////        Doctor doctor = doctorService.getDoctorById(order.getOrderDid());
//        Refund re = null;
//        int refundMoney = 0;
//        if ((order.getOrderProceedTime() < 14 * 60) && (order.getOrderType() == 0)) {
//            refundMoney = 10;
////            refundMoney = doctor.getDoctorPrice() - new Double(money).intValue();
//            Pingpp.apiKey = Code.PING_CODE;
//            Pingpp.privateKey = Code.PING_PRIVATE_KEY;
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("description", "视频问诊服务返款");
//            params.put("amount", refundMoney);
//            try {
//                Charge ch = Charge.retrieve(order.getOrderChargeId());
//                re = ch.getRefunds().create(params);
//                return re;
//            } catch (AuthenticationException e) {
//                e.printStackTrace();
//            } catch (InvalidRequestException e) {
//                e.printStackTrace();
//            } catch (APIConnectionException e) {
//                e.printStackTrace();
//            } catch (APIException e) {
//                e.printStackTrace();
//            } catch (ChannelException e) {
//                e.printStackTrace();
//            }
//        }
//        order.setOrderRefundMoney(refundMoney);
//        order.setOrderPayStatus(OrderCode.ORDER_REFUNDED);
//        orderService.saveOrUpdateOrder(order);
////        int initDoctorMoney = doctor.getDoctorMoney() == null ? 0 : doctor.getDoctorMoney();
////        int doctorMoney = order.getOrderPaidMoney() - refundMoney + initDoctorMoney;
////        doctor.setDoctorMoney(doctorMoney);
////        doctorService.updateDoctor(doctor);
//        return re;
//    }

    @Override
    public String transferOrder(Integer amount, String channel, String bankNo,
                                String bankUserName, String openBank, String bankProv, String bankCity,
                                String wxUserOpenId, String wxUserName) {
        try {
//            SeeCash seeCash = new SeeCash();
//            Assert.notNull(doctor, "doctor can not be null");
//            if (amount <= 0 || amount > doctor.getDoctorMoney()) {
//                throw new Exception("amount should be greater than 0");
//            }
            if (channel == PayCode.WX_CHANNEL) {
                Assert.notNull(wxUserOpenId, "wxUserOpenId can not be null for wx pay");
            } else if (channel == PayCode.UNION_PAY_CHANNEL) {
                Assert.notNull(bankNo, "bankNo can not be null for wx pay");
                Assert.notNull(bankUserName, "bankUserName can not be null for wx pay");
                Assert.notNull(openBank, "openBank can not be null for wx pay");
                Assert.notNull(bankProv, "bankProv can not be null for wx pay");
                Assert.notNull(bankCity, "bankCity can not be null for wx pay");
            }
//            seeCash.setDoctorId(doctor.getDoctorId());
//            seeCash.setSeeCashAmount(amount);
//            seeCash.setBankId(bankNo);
//            seeCash.setBankUserName(bankUserName);
//            seeCash.setSeeCashChannel(channel);
//            seeCash.setBankProv(bankProv);
//            seeCash.setBankCity(bankCity);
//            seeCash.setWxUserName(wxUserName);
//            seeCash.setWxUserOpenId(wxUserOpenId);
//            seeCash.setOpenBank(openBank);
//            seeCash.setSeeCashStatus(PayCode.NEW);
//            seeCashDao.saveOrUpdateSeeCash(seeCash);
//            Transfer transfer = getTransfer(seeCash);
//            seeCash.setTransferId(transfer.getId());
//            seeCashDao.saveOrUpdateSeeCash(seeCash);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

//    private Transfer getTransfer(SeeCash seeCash) {
//        Pingpp.apiKey = Code.PING_CODE;
//        Pingpp.privateKey = Code.PING_PRIVATE_KEY;
//        Map<String, Object> transfer = new HashMap<String, Object>();
//        Transfer transfer1 = null;
//        transfer.put("amount", seeCash.getSeeCashAmount());
//        transfer.put("currency", "cny");
//        transfer.put("type", "b2c");
//        transfer.put("order_no", "transfer" + seeCash.getSeeCashId());
//        transfer.put("channel", seeCash.getSeeCashChannel());
//        if (seeCash.getSeeCashChannel().equals(PayCode.UNION_PAY_CHANNEL)) {
//            transfer.put("card_number", seeCash.getBankId());
//            transfer.put("user_name", seeCash.getBankUserName());
//            transfer.put("open_bank", seeCash.getOpenBank());
//            transfer.put("prov", seeCash.getBankProv());
//            transfer.put("city", seeCash.getBankCity());
//            transfer.put("term_type", "08");
//        } else if (seeCash.getSeeCashChannel().equals(PayCode.WX_CHANNEL)) {
//            System.out.println("@@@@@@"+seeCash.getWxUserOpenId());
//            transfer.put("recipient", seeCash.getWxUserOpenId());
//            if (seeCash.getWxUserName() != null) {
//                transfer.put("user_name", seeCash.getWxUserName());
//            }
//        }
//        transfer.put("description", "提现");
//        Map<String, String> app = new HashMap<String, String>();
//        app.put("id", Code.PING_APP_CODE_E);
//        transfer.put("app", app);
//        try {
//            transfer1 = Transfer.create(transfer);
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//        } catch (InvalidRequestException e) {
//            e.printStackTrace();
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIException e) {
//            e.printStackTrace();
//        } catch (ChannelException e) {
//            e.printStackTrace();
//        }
//        return transfer1;
//    }

//    public static void main(String[] args) {
//
//        try {
//            Map<String, Object> chargeParams = new HashMap<String, Object>();
//            chargeParams.put("order_no", "123456789");
//            chargeParams.put("amount", 1);
//            Map<String, String> app = new HashMap<String, String>();
//            app.put("id", "app_zHW5eTyHuXr54yXv");
//            chargeParams.put("app", app);
//            chargeParams.put("channel", "alipay");
//            chargeParams.put("currency", "cny");
//            chargeParams.put("client_ip", "127.0.0.1");
//            chargeParams.put("subject", "test");
//            chargeParams.put("body", "test");
//            Pingpp.privateKeyPath = "/Users/lizai/Desktop/RSACert/rsa_private_key.pem";
//            Pingpp.apiKey = "sk_test_1yfrT4TqHy1OmfPur10Kqz5S";
//            JSONObject jo = JSONObject.fromObject(Charge.create(chargeParams));
//            System.out.println(jo);
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//        } catch (InvalidRequestException e) {
//            e.printStackTrace();
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIException e) {
//            e.printStackTrace();
//        } catch (ChannelException e) {
//            e.printStackTrace();
//        }
//    }
//
    public static void main(String[] args){
        System.out.println((double) 116 / (15 * 60));
//        (double) 1 * (1-(double) (116 / (15 * 60)));
        int a = new Double((double) 1 * (1-(double) 116 / (15 * 60))).intValue();
        System.out.println(a);
    }

}
