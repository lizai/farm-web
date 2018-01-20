package com.mavenMVC.service.impl;

import com.mavenMVC.dao.ITableTypeDao;
import com.mavenMVC.entity.Tabletype;
import com.mavenMVC.service.ITableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lizai on 15/6/25.
 */
@Service("TableTypeServiceImpl")
@Transactional
public class TableTypeServiceImpl implements ITableTypeService {

    @Autowired
    private ITableTypeDao tableTypeDao;

    @Override
    public List<Tabletype> getAllTableTypes() {
        return tableTypeDao.getList(Tabletype.PROPERTYNAME_TABLE_TYPE_ID, false, 0, Integer.MAX_VALUE, null);
    }
}