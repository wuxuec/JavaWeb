/*
 * 模块:   功能层 <br>
 * 用途:   管理在线人数 <br>
 * 作者:   武家辉 wujh6@asiainfo.com <br>
 * 日期:   2016.09.05 <br>
 * 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
 * 版本:   RCS: $Id$ <br>
 * 说明:	 <br>
 * 历史:	 <br>
 */
package app.util;

/**
 * Admin负责管理在线人数.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class Admin {
    
    /** 单例私有静态对象*/
    private static Admin admin = null;
    /** 同时在线人数*/
    private static int onlineNumber;
    
    /**
     * 实例化Admin并返回该对象
     *
     * @return [Admin] 返回单例模式下的唯一对象
     */
    public static synchronized Admin getInstance() {
        if (admin == null) {
            admin = new Admin();
        }
        return admin;
    }
    
    /**
     * Admin的私有构造函数
     */
    private Admin() {
        onlineNumber = 0;
    }
    
    /**
     * 将在线人数onlineNumber加1
     */
    public void addOnlineNumber() {
        onlineNumber++;
    }
    
    /**
     * 将在线人数onlineNumber减1
     */
    public void downOnlineNumber() {
        onlineNumber--;
        if (onlineNumber < 0) {
            onlineNumber = 0;
        }
    }
    
    /**
     * 获取onlineNumber的值
     * 
     * @return [int] 返回onlineNumber的值
     */
    public int getOnlineNumber() {
        return onlineNumber;
    }
    
}
