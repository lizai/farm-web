package com.mavenMVC.service;

import net.sf.json.JSONObject;

/**
 * Created by lizai on 15/6/25.
 */
public interface IQueueService {

    //返回前面还有多少
    public int pushQueue(long userId, long tableType);

    public JSONObject getMyQueue(long userId);

}
