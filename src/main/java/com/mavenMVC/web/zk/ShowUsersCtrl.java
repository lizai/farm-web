package com.mavenMVC.web.zk;

import com.mavenMVC.entity.User;
import com.mavenMVC.service.IUserService;
import com.mavenMVC.web.zk.renders.UserDataRowRender;
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
public class ShowUsersCtrl extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = Logger.getLogger(String.valueOf(ShowUsersCtrl.class));

    @Wire
    private Window showUsersWin;
    @Wire
    private Grid grid;
    @Wire
    private Button insertUser;
    @Wire
    private Button search;

    @Autowired
    private IUserService iUserService;

    private ListModel<User> users;

    private Map authorityMap = new HashMap();

    public ShowUsersCtrl() {
        logger.info("Dealing with ShowActivitiesCtrl Action...");
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            for (GrantedAuthority at: auth.getAuthorities()) {
                authorityMap.put(at.getAuthority(), "true");
            }
        }
        users = new ListModelList<User>(iUserService.getAllUsers());
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if(authorityMap.get("ROLE_SUPERVISOR")!=null){
            grid.setRowRenderer(new UserDataRowRender());
        }
    }

    @Listen("onClick = #insertUser")
    public void insertTeacher() {
        Window win = (Window) Executions.getCurrent().createComponents("/crm/publishUser.zul", showUsersWin, null);
        // 设置关闭按钮
        win.setTitle("用户详情");
        win.setBorder("normal");
        win.setClosable(true);
        win.doModal();
    }

    @Listen("onClick = #search")
    public void search() {
        users = new ListModelList<User>(iUserService.getAllUsers());
        grid.setModel(users);
        grid.setRowRenderer(new UserDataRowRender());
    }

    public ListModel<User> getUsers() {
        return users;
    }
}
