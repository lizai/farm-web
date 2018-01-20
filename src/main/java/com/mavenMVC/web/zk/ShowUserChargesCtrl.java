package com.mavenMVC.web.zk;

import com.mavenMVC.entity.Charge;
import com.mavenMVC.service.IChargeService;
import com.mavenMVC.service.IUserService;
import com.mavenMVC.web.zk.renders.UserChargesDataRowRender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.util.List;
import java.util.Map;

/**
 * Created by lizai on 15/8/18.
 */
public class ShowUserChargesCtrl extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = Logger.getLogger(String.valueOf(ShowUserChargesCtrl.class));

    @Wire
    private Grid grid;

    @Wire
    private Window win;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IChargeService iChargeService;

    private ListModel<Charge> charges;

    public ShowUserChargesCtrl() {
        logger.info("Dealing with ShowUserChargesCtrl Action...");
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Map map = Executions.getCurrent().getArg();
        if(map.get("accountId")!=null){
            List<Charge> localCharges = iChargeService.getChargesByUserId(Long.parseLong(map.get("accountId").toString()));
            if(localCharges!=null){
                charges = new ListModelList<Charge>(localCharges);
            }else{
                win.detach();
            }
        }
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        grid.setRowRenderer(new UserChargesDataRowRender());
    }

    public ListModel<Charge> getCharges() {
        return charges;
    }
}
