package com.mavenMVC.dao;

import com.mavenMVC.entity.Activityuser;

import java.util.List;

/**
 * Created by lizai on 15/6/24.
 */
public interface IActivityUserDao {

    public List<Activityuser> getByUserIdAndActivityId(Long userId, Long activityId);

    public List<Activityuser> getByUserIdAndActivityId(Long userId, Long activityId, int start, int offset, List<Long> receivedIds);

    public List<Activityuser> getActivitiesByIds(List<Long> ids);

    public void saveOrUpdateActivityuser(Activityuser activityuser);

    public Activityuser getById(Long id);

}
