package com.mavenMVC.web.controller;

import org.springframework.stereotype.Controller;

/**
 * Created by lizai on 15/6/25.
 */
@Controller
//@RequestMapping("/order")
public class OrderControllerBak {

//    protected final Logger logger = Logger.getLogger(String.valueOf(OrderControllerBak.class));
//
//    @Autowired
//    private IOrderService iOrderService;
//
//    @Autowired
//    private IUserService iUserService;
//
//    @Autowired
//    private IPayService iPayService;
//
//    @Autowired
//    private HttpServletRequest request;
//
//    @RequestMapping(value = "/getMyOrders", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
////    @Authorization
//    @ApiOperation(value = "获取当前登录用户（医生或者患者）的订单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
//    })
//    public
//    @ResponseBody
//    String getMyOrders(
//            @CurrentUser Object currentUser,
//            @ApiParam(name = "type", value = "0:当前订单,1:历史")
//            @RequestParam(value = "type", required = false) Integer type,
//            @RequestParam(value = "start") Integer start,
//            @RequestParam(value = "offset") Integer offset,
//            @RequestParam(value = "receivedIds", required = false) List<Long> receivedIds,
//            @RequestParam(value = "orderType", required = false) Integer orderType) {
//        logger.info("Dealing with getMyOrders Action...");
//        RequestManager requestManager = new RequestManager();
//        JSONArray result = new JSONArray();
//        try {
//            Assert.notNull(currentUser, "未登录系统");
//            Assert.notNull(start, "查询起始不能为空");
//            Assert.notNull(offset, "查询终止不能为空");
//            if (start < 0 || offset <= 0 || start > offset) {
//                throw new Exception("查询条件有误");
//            }
//            Long userId, doctorId;
//            List<Order> orders;
//            if (currentUser instanceof User) {
//                userId = ((User) currentUser).getUserId();
//                List<Integer> statuses = new ArrayList<Integer>();
//                if (type == 0) {
//                    statuses.add(OrderCode.ORDER_DOC_ESTABLISHED);
//                    statuses.add(OrderCode.ORDER_NEW);
//                    statuses.add(OrderCode.ORDER_USER_CANCELED);
//                } else if (type == 1) {
//                    statuses.add(OrderCode.ORDER_NORMAL_FINISHED);
//                    statuses.add(OrderCode.ORDER_ABNORMAL_FINISHED);
//                    statuses.add(OrderCode.ORDER_DOC_UNESTABLISHED);
//                }
//                orders = iOrderService.getOrdersByUserId(userId, statuses, null, orderType, start, offset, receivedIds);
//            } else {
//                throw new Exception("当前用户类型错误");
//            }
//            List<JSONObject> orderDetails = new ArrayList<JSONObject>();
//            if (orders != null && orders.size() > 0) {
//                for (Order order : orders) {
//                    JSONObject o = JSONObject.fromObject(order);
//                    User user = iUserService.getUserById(userId);
//                    o.put("orderUserCellphone", user.getUserCellphone());
//                    orderDetails.add(o);
//                }
//            }
//            result = JSONArray.fromObject(orderDetails);
//        } catch (Exception e) {
//            requestManager.putErrorMessage(e.getMessage());
//        } finally {
//            logger.info("Done getMyOrders Action!");
//            return requestManager.printJson(result).toString();
//        }
//    }
//
//    @RequestMapping(value = "/createOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
////    @Authorization
//    @ApiOperation(value = "创建订单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
//    })
//    public
//    @ResponseBody
//    String createOrder(
//            @CurrentUser Object currentUser,
//            @RequestParam(value = "orderDoctorId") Long orderDoctorId,
//            @RequestParam(value = "orderPatientId") Long orderPatientId,
//            @RequestParam(value = "orderType", required = false) Integer orderType,
//            @RequestParam(value = "orderRecordId", required = false) Long orderRecordId) {
//        logger.info("Dealing with createOrder Action...");
//        RequestManager requestManager = new RequestManager();
//        JSONObject result = new JSONObject();
//        try {
//            Assert.notNull(currentUser, "未登录系统");
//            Assert.notNull(orderDoctorId, "orderDoctorId can not be empty");
//            Assert.notNull(orderPatientId, "orderPatientId can not be empty");
//            Long userId;
//            if (currentUser instanceof User) {
//                userId = ((User) currentUser).getUserId();
//            } else {
//                throw new Exception("当前用户类型错误");
//            }
//            if (orderType == null) {
//                orderType = 0;
//            }
//        } catch (Exception e) {
//            requestManager.putErrorMessage(e.getMessage());
//        } finally {
//            logger.info("Done createOrder Action!");
//            return requestManager.printJson(result).toString();
//        }
//    }
//
////
////    @RequestMapping(value = "/finishOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
//////    @Authorization
////    @ApiOperation(value = "结束订单")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
////            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
////            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header"),
////
////    })
////    public
////    @ResponseBody
////    String finishOrder(
////            @CurrentUser Object currentUser,
////            @RequestParam(value = "orderId") Long orderId,
////            @RequestParam(value = "isExceptionFinished", required = false) Integer isExceptionFinished,
////            @RequestParam(value = "orderProceedTime") Long orderProceedTime) {
////        logger.info("Dealing with finishOrder Action...");
////        RequestManager requestManager = new RequestManager();
////        JSONObject result = new JSONObject();
////        try {
////            Assert.notNull(currentUser, "未登录系统");
////            Assert.notNull(orderId, "orderId can not be empty");
////            Long doctorId, userId;
////            Order order = iOrderService.getOrderById(orderId);
////            if (order.getOrderStatus() == OrderCode.ORDER_DOC_ESTABLISHED && order.getOrderPayStatus() == OrderCode.ORDER_PAID) {
////                if (currentUser instanceof Doctor) {
////                    doctorId = ((Doctor) currentUser).getDoctorId();
////                    if (doctorId != order.getOrderDid()) {
////                        throw new Exception("当前医生无权操作该订单");
////                    }
////                }else if (currentUser instanceof User) {
////                    userId = ((User) currentUser).getUserId();
////                    Patient patient = iPatientService.getPatientById(order.getOrderPid());
////                    if (patient == null || patient.getPatientUserId() != userId) {
////                        throw new Exception("当前用户无权操作该订单");
////                    }
////                } else {
////                    throw new Exception("当前用户类型错误");
////                }
////                if (isExceptionFinished != null && isExceptionFinished > 0) {
////                    order.setOrderStatus(OrderCode.ORDER_ABNORMAL_FINISHED);
////                }else{
////                    order.setOrderStatus(OrderCode.ORDER_NORMAL_FINISHED);
////                }
////                order.setOrderProceedTime(orderProceedTime);
////                iOrderService.saveOrUpdateOrder(order);
////                result = JSONObject.fromObject(order);
////            } else {
////                throw new Exception("当前订单未确认或未支付");
////            }
////        } catch (Exception e) {
////            requestManager.putErrorMessage(e.getMessage());
////        } finally {
////            logger.info("Done finishOrder Action!");
////            return requestManager.printJson(result).toString();
////        }
////    }
//
//    @RequestMapping(value = "/payOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
////    @Authorization
//    @ApiOperation(value = "支付订单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
//    })
//    public
//    @ResponseBody
//    String payOrder(
//            @CurrentUser Object currentUser,
//            @RequestParam(value = "orderId") Long orderId,
//            @RequestParam(value = "orderMoney") Integer orderMoney,
//            @RequestParam(value = "orderChannel") String orderChannel) {
//        logger.info("Dealing with payOrder Action...");
//        RequestManager requestManager = new RequestManager();
//        JSONObject result = new JSONObject();
//        try {
//            Assert.notNull(currentUser, "未登录系统");
//            Long userId;
//            if (currentUser instanceof User) {
//                userId = ((User) currentUser).getUserId();
//            } else {
//                throw new Exception("当前用户类型错误");
//            }
//            Order order = iOrderService.getOrderById(orderId);
////            if (order.getOrderPayStatus() == OrderCode.ORDER_UNPAID) {
////                String ip = RequestUtil.getIpAddress(request);
////                Charge charge = iPayService.payOrder(orderId, orderMoney, orderChannel, ip);
////                logger.info(charge);
////                order.setOrderChargeId(charge.getId());
////                order.setOrderPayChannel(orderChannel);
////                iOrderService.saveOrUpdateOrder(order);
////                String resultString = "{\"error_code\":0,\"error_message\":\"\",\"data\":"+charge.toString()+"}";
////                logger.info("Done payOrder Action!");
////                return resultString;
////            } else {
////                throw new Exception("订单已支付");
////            }
//            return "";
//        } catch (Exception e) {
//            logger.info("Done payOrder Action!");
//            requestManager.putErrorMessage(e.getMessage());
//            e.printStackTrace();
//            return requestManager.printJson(result).toString();
//        } finally {
//            logger.info("Done payOrder Action!");
//        }
//    }
//
//    @RequestMapping(value = "/payOrderSuccess", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
////    @Authorization
//    @ApiOperation(value = "支付订单成功")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header")
//    })
//    public
//    @ResponseBody
//    String payOrderSuccess(@RequestBody String Webhook) {
//        logger.info("Done payOrderSuccess Action!");
//        try {
//            Event event = Webhooks.eventParse(Webhook);
//            if (event != null) {
//                if ("charge.succeeded".equals(event.getType())) {
//                    EventData eventData = event.getData();
//                    PingppObject chargeRsObj = eventData.getObject();
//                    String objectString = chargeRsObj.toString();
//                    logger.info(objectString);
//                    JSONObject jsonObject = JSONObject.fromObject(objectString);
//                    Long orderId = jsonObject.getLong("order_no");
//                    Integer paidMoney = jsonObject.getInt("amount");
//                    Order order = iOrderService.getOrderById(orderId);
////                    if (order != null && order.getOrderPayStatus() == OrderCode.ORDER_UNPAID) {
////                        order.setOrderPayStatus(OrderCode.ORDER_PAID);
////                        order.setOrderPaidMoney(paidMoney);
////                        iOrderService.saveOrUpdateOrder(order);
////                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            logger.info("Done payOrderSuccess Action!");
//            return null;
//        }
//    }

//    @RequestMapping(value = "/commentOrder", produces = "text/json; charset=utf-8", method = {RequestMethod.POST, RequestMethod.GET})
////    @Authorization
//    @ApiOperation(value = "评论订单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "sig", value = "sig", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "requestTime", value = "requestTime", required = true, dataType = "string", paramType = "header"),
//
//    })
//    public
//    @ResponseBody
//    String commentOrder(
//            @CurrentUser Object currentUser,
//            @RequestParam(value = "orderId") Long orderId,
//            @RequestParam(value = "orderScore") Integer orderScore,
//            @RequestParam(value = "orderComment") String orderComment) {
//        logger.info("Dealing with commentOrder Action...");
//        RequestManager requestManager = new RequestManager();
//        JSONObject result = new JSONObject();
//        try {
//            Assert.notNull(currentUser, "未登录系统");
//            Assert.notNull(orderId, "orderDoctorId can not be empty");
//            Assert.notNull(orderScore, "orderScore can not be empty");
//            Assert.notNull(orderComment, "orderComment can not be empty");
//            Long userId;
//            if (currentUser instanceof User) {
//                userId = ((User) currentUser).getUserId();
//            } else {
//                throw new Exception("当前用户类型错误");
//            }
//            Order order = iOrderService.getOrderById(orderId);
//            if (order != null) {
//                if(order.getOrderStatus() == OrderCode.ORDER_NORMAL_FINISHED && order.getOrderPayStatus() == OrderCode.ORDER_PAID ){
//                    User user = iUserService.getUserById(userId);
//                    if (userId != user.getUserId()) {
//                        throw new Exception("当前用户不能评论该订单");
//                    }
//                    order.setOrderScore(orderScore);
//                    order.setOrderComment(orderComment);
//                    iOrderService.saveOrUpdateOrder(order);
//                    //给医生评分
//                    Doctor doctor = iDoctorService.getDoctorById(order.getOrderDid());
//                    if (doctor != null) {
//                        //给用户返现并记录医生所得款
//                        iPayService.refundOrder(order);
//                        List<Integer> statues = new ArrayList<Integer>();
//                        statues.add(OrderCode.ORDER_NORMAL_FINISHED);
//                        List<Integer> pstatues = new ArrayList<Integer>();
//                        pstatues.add(OrderCode.ORDER_SETTLED);
//                        pstatues.add(OrderCode.ORDER_REFUNDED);
//                        List<Order> orders = iOrderService.getOrdersByDoctorId(doctor.getDoctorId(), statues, pstatues,null, 0, 100, null);
//                        int score = 0;
//                        for (Order order1 : orders) {
//                            if (order1.getOrderScore() != null) {
//                                score += order1.getOrderScore();
//                            }
//                        }
//                        score += orderScore;
//                        score = score / (orders.size() + 1);
//                        doctor.setDoctorScore(score);
//                        iDoctorService.updateDoctor(doctor);
//                    }
//                }else{
//                    throw new Exception("订单不可评论");
//                }
//            }
//        } catch (Exception e) {
//            requestManager.putErrorMessage(e.getMessage());
//        } finally {
//            logger.info("Done commentOrder Action!");
//            return requestManager.printJson(result).toString();
//        }
//    }
}

