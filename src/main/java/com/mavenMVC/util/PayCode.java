package com.mavenMVC.util;

/**
 * Created by lizai on 15/6/11.
 */
public class PayCode {

    /**
     * 存储提现渠道
     */
    public static final String WX_CHANNEL = "wx";

    public static final String UNION_PAY_CHANNEL = "unionpay";

    /**
     * 存储提现状态
     */
    public static final Integer NEW = 0;

    public static final Integer SUCCESS = 1;

    public static final Integer CANCEL = 2;

    public static final Integer FAIL = 3;

}
