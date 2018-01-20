package com.mavenMVC.service;

import com.mavenMVC.entity.Book;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
public interface IBookService {

    public boolean bookDinner(long userId, long dinnerTime, int peopleNum, long tableType,
                              String name, String cellphone, String comment);

    public List<Book> getMyBooks(long userId, int start, int offset, List<Long> receivedIds);

}
