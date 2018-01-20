package com.mavenMVC.web.zk;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.dao.IDishTypeDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.entity.Dishtype;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;


/**
 * Created by lizai on 15/8/13.
 */

public class PublishDishViewCtrl extends SelectorComposer<Component> {

    protected final Logger logger = Logger.getLogger(String.valueOf(PublishDishViewCtrl.class));

    @Wire
    private Window win;
    @Wire
    private Textbox nameTextbox;
    @Wire
    private Intbox priceIntbox;
    @Wire
    private Selectbox typeSelectbox;
    @Wire
    private Image picsSmall;
    @Wire
    private Button submitBtn;
    @Autowired
    private IDishDao iDishDao;

    @Autowired
    private IDishTypeDao iDishTypeDao;

    private Dish dishEntity;

    private Window parentWindow;

    private ListModel<Dishtype> dishTypes;

    public PublishDishViewCtrl() {
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Map map = Executions.getCurrent().getArg();
        dishTypes = new ListModelList<Dishtype>(iDishTypeDao.getList(null, true, 0, Integer.MAX_VALUE, null));
        if(map.get("accountId")!=null){
            dishEntity = iDishDao.getById(Long.parseLong(map.get("accountId").toString()));
            for(Dishtype i : ((ListModelList<Dishtype>) dishTypes)){
                if(i.getDishTypeId().equals(dishEntity.getDishType())){
                    ((ListModelList<Dishtype>)dishTypes).addToSelection(i);
                }
            }
        }else{
            dishEntity = new Dish();
            dishEntity.setDishType(dishTypes.getElementAt(0).getDishTypeId());
            ((ListModelList<Dishtype>)dishTypes).addToSelection(((ListModelList<Dishtype>) dishTypes).get(0));
        }
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        logger.info("Dealing with PublishUserViewCtrl Action...");
        if(dishEntity!=null)
            priceIntbox.setValue(dishEntity.getDishPrice()/100);
        else
            priceIntbox.setValue(0);
        parentWindow = (Window) win.getParent();
    }

    @Listen("onChange = #nameTextbox")
    public void nameTextbox() {
        String name = nameTextbox.getValue();
        dishEntity.setDishName(name);
    }

    @Listen("onChange = #priceIntbox")
    public void priceIntbox() {
        Integer price = priceIntbox.getValue();
        dishEntity.setDishPrice(price*100);
    }

    @Listen("onSelect = #typeSelectbox")
    public void typeSelectbox() {
        Set<Dishtype> types = ((ListModelList<Dishtype>)dishTypes).getSelection();
        Dishtype type = types.iterator().next();
        dishEntity.setDishType(type.getDishTypeId());
    }

    @Listen("onClick = #submitBtn")
    public void submit() {
        logger.info("do submit function");
        if ((dishEntity.getDishName() != null) && (dishEntity.getDishPrice() != null)) {
            dishEntity.setDishStatus(0);
            org.zkoss.image.Image image = picsSmall.getContent();
            if (image != null) {
                String filename = "dish_" + Calendar.getInstance().getTimeInMillis() + ".png";
                String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("");
                path = path.substring(0, path.lastIndexOf("/")) + "/files/dishes/";
                new File(path).mkdir();
                path += filename;
                File file = new File(path);
                try {
                    Files.copy(file, image.getStreamData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fileNameUrl = Executions.getCurrent().getScheme() + "://" + Executions.getCurrent().getServerName() + ":"
                        + Executions.getCurrent().getServerPort() + "/files/dishes/" + filename;
                dishEntity.setDishImage(fileNameUrl);
            }
            if ((dishEntity.getDishId() != null) && (dishEntity.getDishId() > 0)) {
                iDishDao.saveOrUpdateEntity(dishEntity);
                Messagebox.show("菜品信息修改成功");
                win.detach();
                refreshParentWindow();
            } else {
                iDishDao.saveOrUpdateEntity(dishEntity);
                Messagebox.show("菜品信息发布成功");
                win.detach();
                refreshParentWindow();
            }
        } else {
            Messagebox.show("菜品信息不完整");
        }
    }

    public void refreshParentWindow() {
        Button b = (Button) parentWindow.getFellow("search");
        Events.sendEvent(new Event("onClick", b));
    }

    public Dish getDishEntity() {
        return dishEntity;
    }

    public ListModel<Dishtype> getDishTypes() {
        return dishTypes;
    }
}
