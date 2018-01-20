package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IUserDao;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IUserService;
import com.mavenMVC.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lizai on 15/6/10.
 */
@Service("UserServiceImpl")
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public User getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User registerUser(String userName, String password, String cellphone) {
        if(cellphone!=null){
            User userEntity = new User();
            userEntity.setUserCellphone(cellphone);
            if(userName!=null){
                userEntity.setUserName(userName);
            }
            if(password!=null){
                userEntity.setUserPassword(password);
            }
            if(cellphone!=null){
                userEntity.setUserCellphone(cellphone);
            }
            userEntity.setUserStatus(0);
            userEntity.setUserToken(MD5.GetMD5Code(cellphone + Calendar.getInstance().getTimeInMillis()));
            userDao.saveUser(userEntity);
            return userEntity;
        }else{
            return null;
        }
    }

    @Override
    public boolean ifUserCellphoneRegisted(String cellphone) {
        return userDao.ifUserExists(cellphone);
    }

    @Override
    public boolean resetPassword(String cellphone, String password) {
        if(cellphone!=null){
            User userEntity = userDao.getUserByCellphone(cellphone);
            if(userEntity!=null){
                userEntity.setUserPassword(password);
                userDao.updateUser(userEntity);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public User loginValid(String cellphone, String password) {
        User user = userDao.getUserByCellphoneAndPassword(cellphone,password);
        if(user!=null){
            user.setUserLogin(1);
            userDao.updateUser(user);
        }
        return user;
    }

    @Override
    public void updateUser(User userEntity) {
        userDao.updateUser(userEntity);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
