package com.mavenMVC.service;

import com.mavenMVC.entity.Message;

import java.util.List;

/**
 * Created by lizai on 16/5/24.
 */
public interface IMessageService {

    public List<Message> getMessagesByUserAndType(Long userId, Integer type, Integer start, Integer offset, List<Long> receivedIds);

    public List<Message> getMessagesByType(Integer type, Integer start, Integer offset, List<Long> receivedIds);

    public void saveOrUpdateMessage(Message message);

}
