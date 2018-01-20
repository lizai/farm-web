package com.mavenMVC.dao;

import com.mavenMVC.entity.Admin;

/**
 * Created by lizai on 16/4/11.
 */
public interface IAdminDao {

    public Admin getAdminByName(String name);

}
