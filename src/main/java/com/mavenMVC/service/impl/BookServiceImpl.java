package com.mavenMVC.service.impl;

import com.mavenMVC.dao.IBookDao;
import com.mavenMVC.entity.Book;
import com.mavenMVC.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Service("BookServiceImpl")
@Transactional
public class BookServiceImpl implements IBookService {

    @Autowired
    private IBookDao bookDao;

    @Override
    public boolean bookDinner(long userId, long dinnerTime, int peopleNum, long tableType, String name, String cellphone, String comment) {
        try{
            Book book = new Book();
            book.setUserId(userId);
            book.setDinnerTime(dinnerTime);
            book.setBookNum(peopleNum);
            book.setBookTableType(tableType);
            book.setBookName(name);
            book.setBookCellphone(cellphone);
            book.setBookMsg(comment);
            bookDao.saveOrUpdateEntity(book);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Book> getMyBooks(long userId, int start, int offset, List<Long> receivedIds) {
        return bookDao.getListByColumn(userId, Book.PROPERTYNAME_USER_ID,
                start, offset, receivedIds, Book.PROPERTYNAME_CREATE_TIME, true);
    }
}