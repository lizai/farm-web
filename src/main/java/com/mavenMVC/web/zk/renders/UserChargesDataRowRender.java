package com.mavenMVC.web.zk.renders;

import com.mavenMVC.entity.Charge;
import com.mavenMVC.entity.User;
import com.mavenMVC.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lizai on 15/8/18.
 */
public class UserChargesDataRowRender implements RowRenderer{

    public UserChargesDataRowRender(){
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Autowired
    private IUserService iUserService;

    @Override
    public void render(Row row, Object o, int i) throws Exception {
        if(o==null)
            return;
        if(o.getClass() == Charge.class){
            Charge charge = (Charge) o;
            User user = iUserService.getUserById(charge.getUserId());
            if(charge!=null && user != null){
                new Label(""+user.getUserName()).setParent(row);
                new Label(""+charge.getChargePrice()).setParent(row);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh-ss");
                new Label(""+sdf.format(new Date(charge.getCreateTime()))).setParent(row);
            }else{
                return;
            }
        }
    }
}
