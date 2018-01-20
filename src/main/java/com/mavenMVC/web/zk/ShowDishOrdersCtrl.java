package com.mavenMVC.web.zk;

import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.entity.Dishorder;
import com.mavenMVC.web.zk.renders.DishOrderDataRowRender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizai on 15/8/15.
 */
public class ShowDishOrdersCtrl extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = Logger.getLogger(String.valueOf(ShowDishOrdersCtrl.class));

    @Wire
    private Window showDishOrdersWin;
    @Wire
    private Grid grid;
    @Wire
    private Button insertDishOrder;
    @Wire
    private Button search;

    @Autowired
    private IDishOrderDao iDishOrderDao;

    private ListModel<Dishorder> dishOrders;

    private Map authorityMap = new HashMap();

    public ShowDishOrdersCtrl() {
        logger.info("Dealing with ShowDishOrdersCtrl Action...");
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            for (GrantedAuthority at : auth.getAuthorities()) {
                authorityMap.put(at.getAuthority(), "true");
            }
        }
        dishOrders = new ListModelList<Dishorder>(iDishOrderDao.getList(null, true, 0, Integer.MAX_VALUE, null));
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (authorityMap.get("ROLE_SUPERVISOR") != null) {
            grid.setRowRenderer(new DishOrderDataRowRender());
        }
    }

    @Listen("onClick = #insertDishOrder")
    public void insertDishOrder() {
        Window win = (Window) Executions.getCurrent().createComponents("/crm/publishDishOrder.zul", showDishOrdersWin, null);
        // 设置关闭按钮
        win.setTitle("餐单详情");
        win.setBorder("normal");
        win.setClosable(true);
        win.doModal();
    }

    @Listen("onClick = #search")
    public void search() {
        dishOrders = new ListModelList<Dishorder>(iDishOrderDao.getList(null, true, 0, Integer.MAX_VALUE, null));
        grid.setModel(dishOrders);
        grid.setRowRenderer(new DishOrderDataRowRender());
    }

    public ListModel<Dishorder> getDishOrders() {
        return dishOrders;
    }

}
