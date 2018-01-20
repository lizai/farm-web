package com.mavenMVC.web.zk.renders;

import com.mavenMVC.dao.IDishDao;
import com.mavenMVC.dao.IDishOrderDao;
import com.mavenMVC.dao.IDishTypeDao;
import com.mavenMVC.dao.IUserDao;
import com.mavenMVC.entity.Dishorder;
import com.mavenMVC.entity.Dishtype;
import com.mavenMVC.entity.User;
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
public class DishOrderDataRowRender implements RowRenderer {

    @Autowired
    private IDishOrderDao iDishOrderDao;

    @Autowired
    private IDishDao iDishDao;

    @Autowired
    private IDishTypeDao iDishTypeDao;

    @Autowired
    private IUserDao iUserDao;

    private Window parentWindow;

    private Map<Long,String> typeMap = new ConcurrentHashMap<Long, String>();

    public DishOrderDataRowRender() {
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
        if (o.getClass() == Dishorder.class) {
            final Dishorder dishOrderEntity = (Dishorder) o;
            parentWindow = (Window) row.getParent().getParent().getParent();
            final Long dishOrderId = dishOrderEntity.getDishOrderId();
            final Button deleteBtn = new Button("删除");
            new Label("" + (dishOrderId == null ? "" : dishOrderId)).setParent(row);
            User user = new User();
            if(dishOrderEntity.getUserId() != null && dishOrderEntity.getUserId()!=-1){
                user = iUserDao.getById(dishOrderEntity.getUserId());
            }
            new Label("" + (dishOrderEntity.getUserId() == null ? "" : user.getUserName())).setParent(row);

            if (dishOrderEntity.getDishOrderStatus() != null && dishOrderEntity.getDishOrderStatus() == 0) {
                new Label("新建").setParent(row);
            } else if(dishOrderEntity.getDishOrderStatus() == 1){
                new Label("已支付").setParent(row);
            } else if(dishOrderEntity.getDishOrderStatus() == -1){
                new Label("删除").setParent(row);
            } else{
                new Label("其他");
            }
            new Label("" + (dishOrderEntity.getDishOrderPrice() == null ? "" : dishOrderEntity.getDishOrderPrice()*1.0/100)).setParent(row);

            Div div = new Div();
            final Button thumbBtn = new Button("详情");
            thumbBtn.setParent(div);
            deleteBtn.setParent(div);
            thumbBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    Map<String, String> arg = new HashMap<String, String>();
                    arg.put("accountId", dishOrderEntity.getDishOrderId().toString());
                    // 新创建一个组件，并且传值 map
                    Window win = (Window) Executions.getCurrent().createComponents("/crm/publishDishOrder.zul", parentWindow, arg);
                    // 设置关闭按钮
                    win.setTitle("餐单详情");
                    win.setBorder("normal");
                    win.setClosable(true);
                    // modal：modal与hightlighted模式基本上是相同的。modal模式下，Window之外的组件是不能够操作的（如下图）。
                    win.doModal();
                    refreshParentWindow();
                }
            });
            deleteBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    dishOrderEntity.setDishOrderStatus(-1);
                    iDishOrderDao.saveOrUpdateEntity(dishOrderEntity);
                    refreshParentWindow();
                }
            });
//            unValidBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//                public void onEvent(Event event) throws Exception {
//                    dishEntity.setDishStatus(1);
//                    iDishDao.saveOrUpdateEntity(dishEntity);
//                    validBtn.setDisabled(false);
//                    unValidBtn.setDisabled(true);
//                    refreshParentWindow();
//                }
//            });
            div.setParent(row);
        }
    }

    public void refreshParentWindow() {
        Button b = (Button) parentWindow.getFellow("search");
        Events.sendEvent(new Event("onClick", b));
    }

}
