package com.mavenMVC.dao;

import com.mavenMVC.entity.User;

import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
public interface IUserDao {

    public User getById(Long id);

    public User getUserByName(String name);

    public void saveUser(User user);

    public void updateUser(User user);

    public User getUserByToken(String token);

    public boolean ifUserExists(String cellphone);

    public User getUserByCellphone(String cellphone);

    public User getUserByCellphoneAndPassword(String cellphone, String password);

    public List<User> getUsersByIds(List<Long> ids);

    public List<User> getAllUsers();

}
