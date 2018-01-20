package com.mavenMVC.web.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

/**
 * Created by lizai on 15/7/7.
 */
public class MainViewCtrl extends GenericForwardComposer {

    @Wire
    private Tabbox contentTab;
    @Wire
    private Listbox sysList;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void onSelect$sysList(Event event) {
        showTab(sysList.getSelectedItem().getLabel(), sysList.getSelectedItem()
                .getImage());
        return;
    }

    public void showTab(String name, String image) {
        for (Object obj: contentTab.getTabs().getChildren()) {
            Tab tab = (Tab) obj;
            if (tab.getLabel().equals(name)) {
                tab.setSelected(true);
                return;
            }
        }
        String include = null;
        if (name.equals("用户管理")) {
            include = "showUsers.zul";
        } else if (name.equals("菜品管理")) {
            include = "showDishes.zul";
        } else if (name.equals("餐单管理")) {
            include = "showDishOrder.zul";
        } else if (name.equals("餐厅预定管理")) {
            include = "showArticles.zul";
        } else if (name.equals("超市商品管理")) {
            include = "showArticles.zul";
        } else if (name.equals("订单管理")) {
            include = "showArticles.zul";
        } else if (name.equals("原料管理")) {
            include = "showArticles.zul";
        } else if (name.equals("进单管理")) {
            include = "showArticles.zul";
        } else if (name.equals("销单管理")) {
            include = "showArticles.zul";
        }

        if (include == null)
            return;

        Tab tab = new Tab(name, image);
        contentTab.getTabs().appendChild(tab);
        tab.setClosable(true);
        tab.setSelected(true);

        Tabpanel panel = new Tabpanel();
        Include inc = new Include(include);
        panel.appendChild(inc);
        contentTab.getTabpanels().appendChild(panel);

        contentTab.invalidate();
    }

}
