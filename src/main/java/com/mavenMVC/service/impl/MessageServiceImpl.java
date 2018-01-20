package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IMessageDao;
import com.mavenMVC.entity.Message;
import com.mavenMVC.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lizai on 16/5/24.
 */
@Service("MessageServiceImpl")
@Transactional
public class MessageServiceImpl implements IMessageService{

    @Autowired
    private IMessageDao messageDao;

    @Override
    public List<Message> getMessagesByUserAndType(Long userId, Integer type, Integer start, Integer offset, List<Long> receivedIds) {
        return messageDao.getMessagesByUserIdAndType(userId, type, start, offset, receivedIds);
    }

    @Override
    public List<Message> getMessagesByType(Integer type, Integer start, Integer offset, List<Long> receivedIds) {
        return messageDao.getMessagesByType(type,start,offset,receivedIds);
    }

    @Override
    public void saveOrUpdateMessage(Message message) {
        messageDao.saveOrUpdateMessage(message);
    }
}
