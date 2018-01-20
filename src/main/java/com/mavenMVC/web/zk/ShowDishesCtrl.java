package com.mavenMVC.web.zk;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.web.zk.renders.DishDataRowRender;
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
public class ShowDishesCtrl extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = Logger.getLogger(String.valueOf(ShowDishesCtrl.class));

    @Wire
    private Window showDishesWin;
    @Wire
    private Grid grid;
    @Wire
    private Button insertDish;
    @Wire
    private Button search;

    @Autowired
    private IDishDao iDishDao;

    private ListModel<Dish> dishes;

    private Map authorityMap = new HashMap();

    public ShowDishesCtrl() {
        logger.info("Dealing with ShowDishesCtrl Action...");
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            for (GrantedAuthority at : auth.getAuthorities()) {
                authorityMap.put(at.getAuthority(), "true");
            }
        }
        dishes = new ListModelList<Dish>(iDishDao.getList(null, true, 0, Integer.MAX_VALUE, null));
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (authorityMap.get("ROLE_SUPERVISOR") != null) {
            grid.setRowRenderer(new DishDataRowRender());
        }
    }

    @Listen("onClick = #insertDish")
    public void insertDish() {
        Window win = (Window) Executions.getCurrent().createComponents("/crm/publishDish.zul", showDishesWin, null);
        // 设置关闭按钮
        win.setTitle("菜品详情");
        win.setBorder("normal");
        win.setClosable(true);
        win.doModal();
    }

    @Listen("onClick = #search")
    public void search() {
        dishes = new ListModelList<Dish>(iDishDao.getList(null, true, 0, Integer.MAX_VALUE, null));
        grid.setModel(dishes);
        grid.setRowRenderer(new DishDataRowRender());
    }

    public ListModel<Dish> getDishes() {
        return dishes;
    }

}
