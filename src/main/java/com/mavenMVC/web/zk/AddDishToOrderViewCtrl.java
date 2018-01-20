package com.mavenMVC.web.zk;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.dao.IDishTypeDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.entity.Dishorder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by lizai on 15/8/13.
 */

public class AddDishToOrderViewCtrl extends SelectorComposer<Component> {

    protected final Logger logger = Logger.getLogger(String.valueOf(AddDishToOrderViewCtrl.class));

    @Wire
    private Window addWin;
    @Wire
    private Selectbox dishSelectbox;
    @Wire
    private Button submitBtn;
    @Autowired
    private IDishDao iDishDao;
    @Autowired
    private IDishOrderDao iDishOrderDao;

    @Autowired
    private IDishTypeDao iDishTypeDao;

    private Dishorder dishOrderEntity;

    private Window parentWindow;

    private ListModel<Dish> todishes;

    private Long dishOrderId = (long)0;

    private Dish tobeAddedDish;

    public AddDishToOrderViewCtrl() {
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Map map = Executions.getCurrent().getArg();
        List<Dish> dishList = iDishDao.getListByColumn(0,Dish.PROPERTYNAME_DISH_STATUS,0,Integer.MAX_VALUE,null,null,true);
        todishes = new ListModelList<Dish>(dishList);
        tobeAddedDish = dishList.get(0);
        if(todishes != null){
            ((ListModelList<Dish>)todishes).addToSelection(((ListModelList<Dish>) todishes).get(0));
        }
        if(map.get("accountId")!=null){
            dishOrderId = Long.parseLong(map.get("accountId").toString());
            dishOrderEntity = iDishOrderDao.getById(dishOrderId);
        }
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        logger.info("Dealing with PublishUserViewCtrl Action...");
        parentWindow = (Window) addWin.getParent();
    }

    @Listen("onSelect = #dishSelectbox")
    public void dishSelectbox() {
        Set<Dish> dishesSet = ((ListModelList<Dish>)todishes).getSelection();
        Dish dish = dishesSet.iterator().next();
        tobeAddedDish = dish;
    }

    @Listen("onClick = #submitBtn")
    public void submit() {
        logger.info("do submit function");
        String idString = "";
        if(dishOrderEntity!=null && dishOrderEntity.getDishIds()!=null){
            idString = dishOrderEntity.getDishIds();
            idString += ";";
            idString += tobeAddedDish.getDishId().toString();
        }else{
            idString = tobeAddedDish.getDishId().toString();
        }
        dishOrderEntity.setDishIds(idString);
        dishOrderEntity.setDishOrderPrice(dishOrderEntity.getDishOrderPrice()+tobeAddedDish.getDishPrice());
        iDishOrderDao.saveOrUpdateEntity(dishOrderEntity);
        addWin.detach();
        refreshParentWindow();
        Messagebox.show("菜品添加成功");
    }

    public void refreshParentWindow() {
        Button b = (Button) parentWindow.getFellow("search");
        Events.sendEvent(new Event("onClick", b));
    }

    public ListModel<Dish> getTodishes() {
        return todishes;
    }
}
