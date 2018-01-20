package com.mavenMVC.dao;

import com.mavenMVC.entity.Activity;

import java.util.List;

/**
 * Created by lizai on 15/6/24.
 */
public interface IActivityDao {

    public List<Activity> getAllActivities();

    public List<Activity> getAllActivities(int start, int offset, List<Long> receivedIds);

    public List<Activity> getActivitiesByIds(List<Long> ids);

    public void saveOrUpdateActivity(Activity activityEntity);

    public int getActivityCount();

    public Activity getActivityById(Long id);

    public List<Activity> getAllActivitiesRegardlessStatus();

}
