package com.mavenMVC.service;

import com.mavenMVC.entity.User;

import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
public interface IUserService {

    public User getUserByName(String name);

    public User getUserByToken(String token);

    public User getUserById(Long id);

    public User registerUser(String userName, String password, String cellphone);

    public boolean ifUserCellphoneRegisted(String cellphone);

    public boolean resetPassword(String cellphone, String password);

    public User loginValid(String cellphone, String password);

    public void updateUser(User userEntity);

    public List<User> getAllUsers();

}
