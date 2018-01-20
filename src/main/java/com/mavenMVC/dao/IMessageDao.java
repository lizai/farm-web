package com.mavenMVC.dao;

import com.mavenMVC.entity.Message;

import java.util.List;

/**
 * Created by lizai on 16/5/24.
 */
public interface IMessageDao {

    public void saveOrUpdateMessage(Message message);

    public List<Message> getMessagesByUserIdAndType(Long id, Integer type, Integer start, Integer offset, List<Long> receivedIds);

    public List<Message> getMessagesByType(Integer type, Integer start, Integer offset, List<Long> receivedIds);

}
