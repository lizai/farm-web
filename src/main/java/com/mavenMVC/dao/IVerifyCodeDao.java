package com.mavenMVC.dao;

import com.mavenMVC.entity.Verifycode;

/**
 * Created by lizai on 15/6/10.
 */
public interface IVerifyCodeDao {

    public void saveVerifyCode(Verifycode verifyCode);

    public Verifycode getLastByCellphone(String cellphone);

}
