/*
 * 模块:   数据层 <br>
 * 用途:   封装数据返回到控制层 <br>
 * 作者:   武家辉 wujh6@asiainfo.com <br>
 * 日期:   2016.08.16 <br>
 * 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
 * 版本:   RCS: $Id$ <br>
 * 说明:	 <br>
 * 历史:	武家辉 2016.09.15 合并查询函数，增加注释 <br>
 */

package app.data;

import app.model.User;
import java.io.IOException;

/**
 * DataBase处理控制层的查询请求并与缓存层进行交互.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class DataBase {
    
    /** 单例私有静态对象*/
    private static DataBase database = null;
    /** 数据缓存对象*/
    private static DataCache dataCache = null;
  
    
    /**
     * 实例化DataBase并返回该对象
     *
     * @return [DataBase] 返回单例模式下的唯一对象
     */
    public synchronized static DataBase getInstance() {
            
        if (database == null) {
            database = new DataBase();
        }
        
        return database;
    }
    
    /**
     * 私有构造函数，获取DataCache对象并赋值至dataCache
     */
    private DataBase() {
        dataCache = DataCache.getInstance();
    }
    
    /**
     * 获取DataCache并赋值至dataCache
     * 
     * @param [account]---查询函数所需要的关键字字符串account
     * @return [User] 返回缓存中查询到的User
     * @throws [IOException] 文件读取异常
     */
    public User query(String account) throws IOException {
        User user = dataCache.query(account);
        return user;
    }
    
    /**
     * 将用户信息插入到dataCache中
     * 
     * @param [account]---用户的账号信息account
     * @param [password]---用户的密码password
     * @param [account]---用户的用户名字符串
     * @throws [IOException] 文件读取异常
     */
    public  void insert(String account,
            String password, String username) throws IOException {
        User user = new User(account, password, username);
        dataCache.insert(user);
    }
    
    //根据新旧密码来更新数据文件中的密码信息
    /**
     * 获取DataCache并赋值至dataCache
     * 
     * @param [account]---需要修改密码的字符串account
     * @param [password]---用户需要修改的新密码password
     * @throws [IOException] 文件读取异常
     */
    public void edit(String account, String newPassword)
            throws IOException{
        dataCache.edit(account, newPassword);
    }
    
}
