package com.mavenMVC.web.zk;

import com.mavenMVC.entity.User;
import com.mavenMVC.service.IUserService;
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


/**
 * Created by lizai on 15/8/13.
 */

public class PublishUserViewCtrl extends SelectorComposer<Component> {

    protected final Logger logger = Logger.getLogger(String.valueOf(PublishUserViewCtrl.class));

    @Wire
    private Window win;
    @Wire
    private Textbox nameTextbox;
    @Wire
    private Textbox passwordTextbox;
    @Wire
    private Textbox cellphoneTextbox;
    @Wire
    private Image picsSmall;
    @Wire
    private Textbox addressTextbox;
    @Wire
    private Button submitBtn;
    @Autowired
    private IUserService iUserService;

    private User userEntity;

    private Window parentWindow;

    public PublishUserViewCtrl(){
        SpringUtil.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
        Map map = Executions.getCurrent().getArg();
        if(map.get("accountId")!=null){
            userEntity = iUserService.getUserById(Long.parseLong(map.get("accountId").toString()));
        }else{
            userEntity= new User();
        }
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        logger.info("Dealing with PublishUserViewCtrl Action...");
        parentWindow = (Window) win.getParent();
    }

    @Listen("onChange = #nameTextbox")
    public void nameTextbox() {
        String name = nameTextbox.getValue();
        userEntity.setUserName(name);
    }

    @Listen("onChange = #passwordTextbox")
    public void passwordTextbox() {
        String name = passwordTextbox.getValue();
        userEntity.setUserPassword(name);
    }

    @Listen("onChange = #cellphoneTextbox")
    public void cellphoneTextbox() {
        String name = cellphoneTextbox.getValue();
        userEntity.setUserCellphone(name);
    }

    @Listen("onChange = #addressTextbox")
    public void addressTextbox() {
        String name = addressTextbox.getValue();
        userEntity.setUserAddress(name);
    }

    @Listen("onClick = #submitBtn")
    public void submit() {
        logger.info("do submit function");
        if((userEntity.getUserName()!=null)&&(userEntity.getUserCellphone()!=null)){
            userEntity.setUserStatus(0);
            org.zkoss.image.Image image = picsSmall.getContent();
            if(image!=null) {
                String filename = "user_" + Calendar.getInstance().getTimeInMillis() + ".png";
                String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("");
                path = path.substring(0, path.lastIndexOf("/")) + "/files/users/";
                new File(path).mkdir();
                path += filename;
                File file = new File(path);
                try {
                    Files.copy(file, image.getStreamData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fileNameUrl = Executions.getCurrent().getScheme()+"://"+Executions.getCurrent().getServerName()+":"
                        +Executions.getCurrent().getServerPort()+ "/files/users/"+filename;
                userEntity.setUserHeadimage(fileNameUrl);
            }
            if((userEntity.getUserId()!=null)&&(userEntity.getUserId()>0)){
                iUserService.updateUser(userEntity);
                Messagebox.show("用户信息修改成功");
                win.detach();
                refreshParentWindow();
            }else{
                iUserService.registerUser(userEntity.getUserName(),userEntity.getUserPassword(),userEntity.getUserCellphone());
                Messagebox.show("用户信息发布成功");
                win.detach();
                refreshParentWindow();
            }
        } else{
            Messagebox.show("用户信息不完整");
        }
    }

    public void refreshParentWindow(){
        Button b = (Button) parentWindow.getFellow("search");
        Events.sendEvent(new Event("onClick", b));
    }

    public User getUserEntity() {
        return userEntity;
    }

}
