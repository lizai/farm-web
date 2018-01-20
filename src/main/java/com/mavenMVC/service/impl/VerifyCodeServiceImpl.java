package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IVerifyCodeDao;
import com.mavenMVC.entity.Verifycode;
import com.mavenMVC.service.IVerifyCodeService;
import com.mavenMVC.util.AliMsgBuilder;
import com.mavenMVC.util.HttpRequestUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lizai on 15/6/12.
 */
@Service("VerifyCodeServiceImpl")
@Transactional
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    protected final Logger logger = Logger.getLogger(String.valueOf(VerifyCodeServiceImpl.class));

    @Autowired
    private IVerifyCodeDao verifyCodeDao;

    @Override
    public Verifycode getVerifyCode(String cellphone) {
        Verifycode verifycode = new Verifycode();
        verifycode.setVerifycodeCellpyone(cellphone);
        verifycode.setVerifycodeCode((int) ((Math.random() * 9 + 1) * 1000));
        verifyCodeDao.saveVerifyCode(verifycode);
        try {
            HttpRequestUtil.sendGet(AliMsgBuilder.url, AliMsgBuilder.build(cellphone, verifycode.getVerifycodeCode().toString()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return verifycode;
    }

    @Override
    public boolean useVerifyCode(String cellphone, int verifyCode) {
        if ((cellphone != null) && (verifyCode > 0)) {
            Verifycode verifycode = verifyCodeDao.getLastByCellphone(cellphone);
            if(verifycode.getVerifycodeCode().equals(verifyCode)){
                return true;
            }
        }
        return false;
    }
}
