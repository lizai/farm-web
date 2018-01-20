package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IQueueDao;
import com.mavenMVC.entity.Queue;
import com.mavenMVC.service.IQueueService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Service("QueueServiceImpl")
@Transactional
public class QueueServiceImpl implements IQueueService {

    @Autowired
    private IQueueDao queueDao;

    @Override
    public int pushQueue(long userId, long tableType) {
        List<Object> ucontents = new ArrayList<Object>();
        ucontents.add(userId);
        ucontents.add(0);
        List<String> ucolumns = new ArrayList<String>();
        ucolumns.add(Queue.PROPERTYNAME_USER_ID);
        ucolumns.add(Queue.PROPERTYNAME_QUEUE_CALLED);
        List<Queue> userQueue = queueDao.getListByAndColumns(ucontents, ucolumns, 0, Integer.MAX_VALUE, null, null, true);
        if (userQueue != null && userQueue.size() > 0) {
            return -1;
        }
        List<Object> contents = new ArrayList<Object>();
        contents.add(0);
        contents.add(tableType);
        List<String> columns = new ArrayList<String>();
        columns.add(Queue.PROPERTYNAME_QUEUE_CALLED);
        columns.add(Queue.PROPERTYNAME_QUEUE_TABEL_TYPE);
        List<Queue> queues = queueDao.getListByAndColumns(contents, columns, 0, Integer.MAX_VALUE, null, null, true);
        Queue queue = new Queue();
        queue.setUserId(userId);
        queue.setQueueCalled(0);
        queue.setQueueTabelType(tableType);
        queueDao.saveOrUpdateEntity(queue);
        return queues.size();
    }

    @Override
    public JSONObject getMyQueue(long userId) {
        List<Object> ucontents = new ArrayList<Object>();
        ucontents.add(userId);
        ucontents.add(0);
        List<String> ucolumns = new ArrayList<String>();
        ucolumns.add(Queue.PROPERTYNAME_USER_ID);
        ucolumns.add(Queue.PROPERTYNAME_QUEUE_CALLED);
        List<Queue> userQueue = queueDao.getListByAndColumns(ucontents, ucolumns, 0, 1, null, Queue.PROPERTYNAME_CREATE_TIME, true);
        Queue currentQueue = userQueue.get(0);
        List<Object> contents = new ArrayList<Object>();
        contents.add(0);
        contents.add(currentQueue.getQueueTabelType());
        List<String> columns = new ArrayList<String>();
        columns.add(Queue.PROPERTYNAME_QUEUE_CALLED);
        columns.add(Queue.PROPERTYNAME_QUEUE_TABEL_TYPE);
        List<Queue> queues = queueDao.getListByAndColumns(contents, columns, 0, Integer.MAX_VALUE, null, null, true);
        int front = 0;
        if (queues != null && queues.size() > 0){
            front = queues.size();
        }
        JSONObject jsonObject = JSONObject.fromObject(currentQueue);
        jsonObject.put("frontNum",front);
        return jsonObject;
    }
}