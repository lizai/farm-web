package com.mavenMVC.core;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizai on 15/4/29.
 */
public class BaseDAO extends HibernateDaoSupport {
    @Resource
    protected SessionFactory sessionFactory;

    /**
     * @Description: HQL查询分页
     * @param nStartRow 开始行数
     * @param nRowSize 数量
     * @param strHQL HQL语句
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:05:58
     * @return List<?>    返回类型
     */
    public List<?> getListByHQL(int nStartRow, int nRowSize, String strHQL, String... strParams){
        //log.debug("HQL：" + strHQL + "\n Params：" + ArrayUtils.toString(strParams));
        Query query=this.sessionFactory.getCurrentSession().createQuery(strHQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }
        if (nRowSize > 0 && nStartRow > -1) {
            query.setFirstResult(nStartRow);
            query.setMaxResults(nRowSize);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        List<?> objList = query.list();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());

        return objList;
    }

    /**
     * @Description: 根据HQL语句查询返回Object
     * @param strHQL HQL语句
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:08:47
     * @return Object    返回类型
     */
    public Object getObjectByHQL(String strHQL, String... strParams){
        //log.debug("HQL：" + strHQL + "\n Params：" + ArrayUtils.toString(strParams));

        Query query = this.sessionFactory.getCurrentSession().createQuery(strHQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        Object objResult = query.uniqueResult();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());

        return objResult;
    }


    /**
     * @Description: 根据HQL语句执行增删改
     * @param strHQL HQL语句
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:10:57
     * @return boolean    返回类型
     */
    public boolean doCUDByHQL(String strHQL, String... strParams){
        //log.debug("HQL：" + strHQL + "\n Params：" + ArrayUtils.toString(strParams));

        Query query =  this.sessionFactory.getCurrentSession().createQuery(strHQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        int influenceRowCount = query.executeUpdate();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());

        if (influenceRowCount > 0) {
            return true;
        }
        return false;
    }


    /**
     * @Description: 保存或者更新对象
     * @param obj 对象实体
     * @throws Exception
     * @date 2014-7-28 下午3:12:52
     * @return void    返回类型
     */
    public void doSaveObject(Object obj){
        //log.debug("Object：" + obj.getClass());
        this.getHibernateTemplate().saveOrUpdate(obj);
    }


    /**
     * @Description:保存用户对象
     * @param obj 对象实体
     * @date 2014-6-16 上午10:03:44
     * @return void    返回类型
     */
    public void doSave(Object obj){
        this.getHibernateTemplate().save(obj);
    }


    /**
     * @Description: 根据SQL语句获得对象
     * @param strSQL SQL语句
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:14:21
     * @return Object    返回类型
     */
    public Object getObjectBySQL(String strSQL, String... strParams){
        //log.debug("SQL：" + strSQL + "\n Params：" + ArrayUtils.toString(strParams));

        Query query = this.sessionFactory.getCurrentSession().createSQLQuery(strSQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        Object objResult = query.uniqueResult();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());

        return objResult;
    }


    /**
     * @Description: 根据指定行数查询数据
     * @param nStartRow 开始数
     * @param nRowSize 数量
     * @param strSQL SQL语句
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:15:49
     * @return List<?>    返回类型
     */
    public List<?> getListBySQL(int nStartRow, int nRowSize, String strSQL, String... strParams){
        //log.debug("SQL：" + strSQL + "\n Params：" + ArrayUtils.toString(strParams));

        Query query = this.sessionFactory.getCurrentSession().createSQLQuery(strSQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }
        if (nRowSize > 0 && nStartRow > -1) {
            query.setFirstResult(nStartRow);
            query.setMaxResults(nRowSize);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        List<?> objList = query.list();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());

        return objList;
    }


    /**
     * @Description: 执行存储过程
     * @param strPROCSQL 存储过程
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:19:13
     * @return boolean    返回类型
     */
    public boolean doProcUpdate(String strPROCSQL, String... strParams){
        //log.debug("PROCSQL：" + strPROCSQL + "\n Params：" + ArrayUtils.toString(strParams));

        Query query = this.sessionFactory.getCurrentSession().createSQLQuery(strPROCSQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        int influenceRowCount = query.executeUpdate();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());

        if (influenceRowCount > 0) {
            return true;
        }
        return false;
    }


    /**
     * @Description: 执行带有返回值的存储过程
     * @param strPROCSQL SQL语句
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:20:26
     * @return List<?>    返回类型
     */
    public List<?> getProcResult(String strPROCSQL, String... strParams){
        //log.debug("PROCSQL：" + strPROCSQL + "\n Params：" + ArrayUtils.toString(strParams));

        Query query = this.sessionFactory.getCurrentSession().createSQLQuery(strPROCSQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        List<?> list = query.list();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());

        return list;

    }


    /**
     * @Description: 执行未有参数的存储过程
     * @param strPROCSQL SQL语句
     * @param strParams 参数
     * @throws Exception
     * @date 2014-7-28 下午3:21:24
     * @return void    返回类型
     */
    public void doProcQuery(String strPROCSQL, String... strParams){
        //log.debug("PROCSQL：" + strPROCSQL + "\n Params：" + ArrayUtils.toString(strParams));

        Query query = this.sessionFactory.getCurrentSession().createSQLQuery(strPROCSQL);
        for (int j = 0; j < strParams.length; j++) {
            query.setString(j, strParams[j]);
        }

        //log.debug("BEGIN：" + DateUtils.getStrOfDateMinute());
        query.list();
        //log.debug("END：" + DateUtils.getStrOfDateMinute());
    }


    /**
     * @Description: 清空当前session操作
     * @date 2014-7-28 下午3:21:54
     * @return void    返回类型
     */
    public void doSessionClear() {
        this.sessionFactory.getCurrentSession().clear();
    }
}

