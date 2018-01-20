package com.mavenMVC.web.zk.renders;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.entity.Dishorder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizai on 15/8/16.
 */
public class DishByOrderDataRowRender implements RowRenderer {

    @Autowired
    private IDishDao iDishDao;
    @Autowired
    private IDishOrderDao iDishOrderDao;

    private Window parentWindow;

    private Dishorder dishorder;

    public DishByOrderDataRowRender(Dishorder dishorder) {
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        this.dishorder = dishorder;
    }

    @Override
    public void render(Row row, Object o, int i) throws Exception {
        if (o == null)
            return;
        if (o.getClass() == Dish.class) {
            final Dish dishEntity = (Dish) o;
            parentWindow = (Window) row.getParent().getParent().getParent();
            final Long dishId = dishEntity.getDishId();
            new Label("" + (dishEntity.getDishName() == null ? "" : dishEntity.getDishName())).setParent(row);
            new Label("" + (dishEntity.getDishPrice() == null ? "" : dishEntity.getDishPrice()*1.0/100)).setParent(row);
            Div div = new Div();
            final Button deleteBtn = new Button("删除");
            deleteBtn.setParent(div);
            deleteBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    String idString = dishorder.getDishIds();
                    String[] idsStrArray = idString.split(";");
                    List<Long> ids = new ArrayList<Long>();
                    for(String idS : idsStrArray){
                        ids.add(Long.parseLong(idS));
                    }
                    ids.remove(dishId);
                    String newIds= StringUtils.join(ids, ";");
                    dishorder.setDishIds(newIds);
                    dishorder.setDishOrderPrice(dishorder.getDishOrderPrice()-dishEntity.getDishPrice());
                    iDishOrderDao.saveOrUpdateEntity(dishorder);
                    refreshParentWindow();
                }
            });
            div.setParent(row);
        }
    }

    public void refreshParentWindow() {
        Button b = (Button) parentWindow.getFellow("search");
        Events.sendEvent(new Event("onClick", b));
    }
}
