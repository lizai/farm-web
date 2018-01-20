package com.mavenMVC.service;

import com.mavenMVC.entity.Verifycode;

/**
 * Created by lizai on 15/6/12.
 */
public interface IVerifyCodeService {

    public Verifycode getVerifyCode(String cellphone);

    public boolean useVerifyCode(String cellphone, int verifyCode);

}
