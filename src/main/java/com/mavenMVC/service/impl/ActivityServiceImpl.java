package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IActivityDao;
import com.mavenMVC.dao.IActivityUserDao;
import com.mavenMVC.entity.Activity;
import com.mavenMVC.entity.Activityuser;
import com.mavenMVC.service.IActivityService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Service("ActivityServiceImpl")
@Transactional
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private IActivityDao activityDao;

    @Autowired
    private IActivityUserDao activityUserDao;

    @Override
    public List<Activity> getAllActivities() {
        return activityDao.getAllActivities();
    }

    @Override
    public List<Activity> getActivities(int start, int offset, List<Long> receivedIds) {
        return activityDao.getAllActivities(start, offset, receivedIds);
    }

    @Override
    public boolean saveOrUpdateActivity(Activity activityEntity) {
        try {
            activityDao.saveOrUpdateActivity(activityEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Activity getActivityById(long id) {
        return activityDao.getActivityById(id);
    }

    @Override
    public int getActivityCount() {
        return activityDao.getActivityCount();
    }

    @Override
    public List<Activity> getActivitiesByUserId(Long uid, Integer start, Integer offset, List<Long> receivedIds) {
        List<Activityuser> activityusers = activityUserDao.getByUserIdAndActivityId(uid, (long) -1, start, offset, receivedIds);
        if (activityusers == null) {
            return null;
        }
        List<Long> acIds = new ArrayList<Long>();
        for (Activityuser ac : activityusers) {
            acIds.add(ac.getActivityAId());
        }
        return activityDao.getActivitiesByIds(acIds);
    }

    @Override
    public List<Activity> getAllActivitiesRegardlessStatus() {
        return activityDao.getAllActivitiesRegardlessStatus();
    }

    @Override
    public boolean takePartInActivity(Activityuser activityuser) {
        try {
            activityUserDao.saveOrUpdateActivityuser(activityuser);
            Activity activity = activityDao.getActivityById(activityuser.getActivityAId());
            activity.setActivityNum(activity.getActivityNum() + 1);
            activityDao.saveOrUpdateActivity(activity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private JSONObject fillActivityIntoJson(Activity activity) {
        JSONObject jo = new JSONObject();
        jo.put("id", activity.getActivityId());
        jo.put("title", activity.getActivityTitle());
//        jo.put("description",activity.getActivityDescription());
//        jo.put("address",activity.getActivityAddress());
//        jo.put("bigImage",activity.getActivityBigimage());
//        jo.put("cost",activity.getActivityCost());
//        jo.put("image",activity.getActivityImage());
//        jo.put("player",activity.getActivityPlayer());
//        jo.put("ticketUrl",activity.getActivityTicketUrl());
//        jo.put("startTime",activity.getActivityStartTime());
//        jo.put("endTime",activity.getActivityEndTime());
//        jo.put("type",activity.getActivityType());
//        jo.put("top",activity.getActivityTop());
//        jo.put("sponsor",activity.getActivitySponsor());
        jo.put("num", activity.getActivityNum());
        return jo;
    }
}