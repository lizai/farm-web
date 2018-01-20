package com.mavenMVC.web.zk.renders;

import com.mavenMVC.entity.User;
import com.mavenMVC.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizai on 15/8/16.
 */
public class UserDataRowRender implements RowRenderer {

    @Autowired
    private IUserService iUserService;

    private Window parentWindow;

    public UserDataRowRender() {
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void render(Row row, Object o, int i) throws Exception {
        if (o == null)
            return;
        if (o.getClass() == User.class) {
            final User userEntity = (User) o;
            parentWindow = (Window) row.getParent().getParent().getParent();
            final Long userId = userEntity.getUserId();
            new Label("" + (userEntity.getUserName() == null ? "" : userEntity.getUserName())).setParent(row);
            new Label("" + (userEntity.getUserCellphone() == null ? "" : userEntity.getUserCellphone())).setParent(row);
            new Label("" + (userEntity.getUserAddress() == null ? "" : userEntity.getUserAddress())).setParent(row);
            if (userEntity.getUserMoney() != null && userEntity.getUserMoney() > 0) {
                new Label("" + userEntity.getUserMoney()).setParent(row);
            } else {
                new Label("0").setParent(row);
            }
            if (userEntity.getUserLogin() != null && userEntity.getUserLogin() == 1) {
                new Label("已登录").setParent(row);
            } else {
                new Label("未登录").setParent(row);
            }

            Div div = new Div();
            final Button thumbBtn = new Button("详情");
            thumbBtn.setParent(div);
            thumbBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    Map<String, String> arg = new HashMap<String, String>();
                    arg.put("accountId", userEntity.getUserId().toString());
                    // 新创建一个组件，并且传值 map
                    Window win = (Window) Executions.getCurrent().createComponents("/crm/publishUser.zul", parentWindow, arg);
                    // 设置关闭按钮
                    win.setTitle("用户详情");
                    win.setBorder("normal");
                    win.setClosable(true);
                    // modal：modal与hightlighted模式基本上是相同的。modal模式下，Window之外的组件是不能够操作的（如下图）。
                    win.doModal();
                    refreshParentWindow();
                }
            });
            final Button thumbBtn1 = new Button("消费记录");
            thumbBtn1.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    Map<String, String> arg = new HashMap<String, String>();
                    arg.put("accountId", userEntity.getUserId().toString());
                    // 新创建一个组件，并且传值 map
                    Window win = (Window) Executions.getCurrent().createComponents("/crm/userOrders.zul", parentWindow, arg);
                    // 设置关闭按钮
                    win.setTitle("消费记录");
                    win.setBorder("normal");
                    win.setClosable(true);
                    // modal：modal与hightlighted模式基本上是相同的。modal模式下，Window之外的组件是不能够操作的（如下图）。
                    win.doModal();
                    refreshParentWindow();
                }
            });
            thumbBtn1.setParent(div);
            final Button thumbBtn2 = new Button("入资记录");
            thumbBtn2.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                public void onEvent(Event event) throws Exception {
                    Map<String, String> arg = new HashMap<String, String>();
                    arg.put("accountId", userEntity.getUserId().toString());
                    // 新创建一个组件，并且传值 map
                    Window win = (Window) Executions.getCurrent().createComponents("/crm/userCharges.zul", parentWindow, arg);
                    // 设置关闭按钮
                    win.setTitle("入资记录");
                    win.setBorder("normal");
                    win.setClosable(true);
                    // modal：modal与hightlighted模式基本上是相同的。modal模式下，Window之外的组件是不能够操作的（如下图）。
                    win.doModal();
                    refreshParentWindow();
                }
            });
            thumbBtn2.setParent(div);
            div.setParent(row);
        }
    }

    public void refreshParentWindow() {
        Button b = (Button) parentWindow.getFellow("search");
        Events.sendEvent(new Event("onClick", b));
    }
}
