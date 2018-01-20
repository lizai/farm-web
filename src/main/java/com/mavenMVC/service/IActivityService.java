package com.mavenMVC.service;

import com.mavenMVC.entity.Activity;
import com.mavenMVC.entity.Activityuser;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
public interface IActivityService {

    public List<Activity> getAllActivities();

    public List<Activity> getActivities(int start, int offset, List<Long> receivedIds);

    public boolean saveOrUpdateActivity(Activity activityEntity);

    public Activity getActivityById(long id);

    public int getActivityCount();

    public List<Activity> getActivitiesByUserId(Long uid, Integer start, Integer offset, List<Long> receivedIds);

    public List<Activity> getAllActivitiesRegardlessStatus();

    public boolean takePartInActivity(Activityuser activityuser);

}
