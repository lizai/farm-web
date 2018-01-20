package com.mavenMVC.util;

/**
 * Created by lizai on 15/6/11.
 */
public class OrderCode {

    /**
     * service order status
     */
    //待支付 order 0 pay 0
    public static final int SERVICE_STATUS_UNPAY = 0;

    //待审核 order 0  pay 1
    public static final int SERVICE_STATUS_UNCHECK = 4;

    //待问诊 order 1 pay 1
    public static final int SERVICE_STATUS_UNDIAGNOSE = 1;

    //待评价 order 2  pay 1
    public static final int SERVICE_STATUS_UNCOMMENT = 3;

    //已失效(取消和未确认) order 3 4
    public static final int SERVICE_STATUS_UNUSED = 5;

    //已完成 order 2  pay 2 3
    public static final int SERVICE_STATUS_FINISHED = 2;

    /**
     * 存储订单状态
     */
    public static final int ORDER_NEW = 0;

    public static final int ORDER_DOC_ESTABLISHED = 1;

    public static final int ORDER_NORMAL_FINISHED = 2;

    public static final int ORDER_USER_CANCELED = 3;

    public static final int ORDER_DOC_UNESTABLISHED = 4;

    public static final int ORDER_ABNORMAL_FINISHED = 5;

    /**
     * 存储订单支付状态
     */
    public static final int ORDER_UNPAID = 0;

    public static final int ORDER_PAID = 1;

    public static final int ORDER_SETTLED = 2;

    public static final int ORDER_REFUNDED = 3;

}
