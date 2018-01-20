package com.mavenMVC.web.zk;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.dao.IDishTypeDao;
import com.mavenMVC.dao.IUserDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.entity.Dishorder;
import com.mavenMVC.entity.User;
import com.mavenMVC.web.zk.renders.DishByOrderDataRowRender;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lizai on 15/8/13.
 */

public class PublishDishOrderViewCtrl extends SelectorComposer<Component> {

    protected final Logger logger = Logger.getLogger(String.valueOf(PublishDishOrderViewCtrl.class));

    @Wire
    private Window win;
    @Wire
    private Label idLabel;
    @Wire
    private Label nameLabel;
    @Wire
    private Label priceLabel;
    @Wire
    private Grid grid;
    @Wire
    private Button submitBtn;
    @Wire
    private Button search;
    @Wire
    private Button insertDish;

    @Autowired
    private IDishDao iDishDao;
    @Autowired
    private IDishTypeDao iDishTypeDao;
    @Autowired
    private IDishOrderDao iDishOrderDao;
    @Autowired
    private IUserDao iUserDao;


    private Dishorder dishOrderEntity;

    private User userEntity;

    private ListModel<Dish> dishes;

    private Window parentWindow;

    private Long dishOrderId = (long)0;

    public PublishDishOrderViewCtrl() {
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Map map = Executions.getCurrent().getArg();
        if(map.get("accountId")!=null){
            dishOrderId = Long.parseLong(map.get("accountId").toString());
            dishOrderEntity = iDishOrderDao.getById(dishOrderId);
            if(dishOrderEntity!=null && dishOrderEntity.getUserId()!=null && dishOrderEntity.getUserId()!=-1)
                userEntity = iUserDao.getById(dishOrderEntity.getUserId());
            else
                userEntity = new User();
            if(dishOrderEntity!=null && dishOrderEntity.getDishIds()!=null){
                String idString = dishOrderEntity.getDishIds();
                String[] idsStrArray = idString.split(";");
                logger.info("data ::::::::::" + idString);
                logger.info("length1 + ::::::::::" + idsStrArray.length);
                List<Long> ids = new ArrayList<Long>();
                List<Dish> dishesModel = new ArrayList<Dish>();
                for(String idS : idsStrArray){
                    dishesModel.add(iDishDao.getById(Long.parseLong(idS)));
                }
                logger.info("length2 + ::::::::::" + dishesModel.size());
                dishes = new ListModelList<Dish>(dishesModel);
                logger.info("length3 + ::::::::::" + dishes.getSize());
            }
        }else{
            dishOrderEntity = new Dishorder();
            dishOrderEntity.setDishOrderStatus(0);
            dishOrderEntity.setUserId((long)-1);
            dishOrderEntity.setDishOrderPrice(0);
        }
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        logger.info("Dealing with PublishDishOrderViewCtrl Action...");
        parentWindow = (Window) win.getParent();
        if(dishOrderEntity!=null&&dishOrderEntity.getDishOrderPrice()!=null)
            priceLabel.setValue(String.valueOf(dishOrderEntity.getDishOrderPrice()*1.00/100));
        else
            priceLabel.setValue(String.valueOf(0.00));
        grid.setRowRenderer(new DishByOrderDataRowRender(dishOrderEntity));
    }

    @Listen("onClick = #submitBtn")
    public void submit() {
        logger.info("do submit function");
        if ((dishOrderEntity.getDishIds() != null) && (dishOrderEntity.getDishOrderPrice() != null)) {
            if ((dishOrderEntity.getDishOrderId() != null) && (dishOrderEntity.getDishOrderId() > 0)) {
                iDishOrderDao.saveOrUpdateEntity(dishOrderEntity);
                Messagebox.show("餐单信息修改成功");
                win.detach();
                refreshParentWindow();
            } else {
                iDishOrderDao.saveOrUpdateEntity(dishOrderEntity);
                Messagebox.show("餐单信息发布成功");
                win.detach();
                refreshParentWindow();
            }
        } else {
            Messagebox.show("菜品信息不完整");
        }
    }

    @Listen("onClick = #insertDish")
    public void insertDish() {
        Map<String, Object> arg = new HashMap<String, Object>();
        iDishOrderDao.saveOrUpdateEntity(dishOrderEntity);
        dishOrderId = dishOrderEntity.getDishOrderId();
        arg.put("accountId", dishOrderEntity.getDishOrderId());
        Window win = (Window) Executions.getCurrent().createComponents("/crm/addDish.zul", this.win, arg);
        // 设置关闭按钮
        win.setTitle("增加菜品");
        win.setBorder("normal");
        win.setClosable(true);
        win.doModal();
    }

    @Listen("onClick = #search")
    public void search() {
        dishOrderEntity = iDishOrderDao.getById(dishOrderId);
        if(dishOrderEntity!=null && dishOrderEntity.getUserId()!=null && dishOrderEntity.getUserId()!=-1)
            userEntity = iUserDao.getById(dishOrderEntity.getUserId());
        else
            userEntity = new User();
        if(dishOrderEntity!=null && dishOrderEntity.getDishIds()!=null){
            String idString = dishOrderEntity.getDishIds();
            String[] idsStrArray = idString.split(";");
            List<Long> ids = new ArrayList<Long>();
            List<Dish> dishesModel = new ArrayList<Dish>();
            for(String idS : idsStrArray){
                Dish dish = iDishDao.getById(Long.parseLong(idS));
                dishesModel.add(dish);
            }
            dishes = new ListModelList<Dish>(dishesModel);
        }
        grid.setModel(dishes);
        grid.setRowRenderer(new DishByOrderDataRowRender(dishOrderEntity));
        if(dishOrderEntity!=null)
            priceLabel.setValue(String.valueOf(dishOrderEntity.getDishOrderPrice()*1.00/100));
        else
            priceLabel.setValue(String.valueOf(0.00));
    }

    public void refreshParentWindow() {
        Button b = (Button) parentWindow.getFellow("search");
        Events.sendEvent(new Event("onClick", b));
    }

    public Dishorder getDishOrderEntity() {
        return dishOrderEntity;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public ListModel<Dish> getDishes() {
        return dishes;
    }
}
