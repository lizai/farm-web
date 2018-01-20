package com.mavenMVC.web.zk.renders;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.dao.IDishTypeDao;
import com.mavenMVC.entity.Dish;
import com.mavenMVC.entity.Dishtype;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lizai on 15/8/16.
 */
public class DishDataRowRender implements RowRenderer {

    @Autowired
    private IDishDao iDishDao;

    @Autowired
    private IDishTypeDao iDishTypeDao;

    private Window parentWindow;

    private Map<Long,String> typeMap = new ConcurrentHashMap<Long, String>();

    public DishDataRowRender() {
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        List<Dishtype> dishtypeList = iDishTypeDao.getList(null,true,0,Integer.MAX_VALUE,null);
        if(dishtypeList!=null){
            for(Dishtype dishtype : dishtypeList){
                typeMap.put(dishtype.getDishTypeId(),dishtype.getDishTypeName());
            }
        }
    }

    @Override
    public void render(Row row, Object o, int i) throws Exception {
        if (o == null)
            return;
        if (o.getClass() == Dish.class) {
            final Dish dishEntity = (Dish) o;
            parentWindow = (Window) row.getParent().getParent().getParent();
            final Long dishId = dishEntity.getDishId();
            final Button validBtn = new Button("生效");
            final Button unValidBtn = new Button("暂停");
            new Label("" + (dishEntity.getDishName() == null ? "" : dishEntity.getDishName())).setParent(row);
            new Label("" + (dishEntity.getDishPrice() == null ? "" : dishEntity.getDishPrice()*1.0/100)).setParent(row);
            if (dishEntity.getDishStatus() != null && dishEntity.getDishStatus() == 0) {
                new Label("有效").setParent(row);
            } else {
                new Label("无效").setParent(row);
            }
            if(dishEntity.getDishType()!=null && typeMap.containsKey(dishEntity.getDishType())){
                new Label("" + typeMap.get(dishEntity.getDishType())).setParent(row);
            }else{
                new Label("").setParent(row);
            }

            Div div = new Div();
            final Button thumbBtn = new Button("详情");
            thumbBtn.setParent(div);
            validBtn.setParent(div);
            unValidBtn.setParent(div);
            thumbBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    Map<String, String> arg = new HashMap<String, String>();
                    arg.put("accountId", dishEntity.getDishId().toString());
                    // 新创建一个组件，并且传值 map
                    Window win = (Window) Executions.getCurrent().createComponents("/crm/publishDish.zul", parentWindow, arg);
                    // 设置关闭按钮
                    win.setTitle("菜品详情");
                    win.setBorder("normal");
                    win.setClosable(true);
                    // modal：modal与hightlighted模式基本上是相同的。modal模式下，Window之外的组件是不能够操作的（如下图）。
                    win.doModal();
                    refreshParentWindow();
                }
            });
            validBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    dishEntity.setDishStatus(0);
                    iDishDao.saveOrUpdateEntity(dishEntity);
                    validBtn.setDisabled(true);
                    unValidBtn.setDisabled(false);
                    refreshParentWindow();
                }
            });
            unValidBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    dishEntity.setDishStatus(1);
                    iDishDao.saveOrUpdateEntity(dishEntity);
                    validBtn.setDisabled(false);
                    unValidBtn.setDisabled(true);
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
