/*
 * 模块:   数据层 <br>
 * 用途:   管理缓存数据 <br>
 * 作者:   武家辉 wujh6@asiainfo.com <br>
 * 日期:   2016.09.05 <br>
 * 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
 * 版本:   RCS: $Id$ <br>
 * 说明:	 <br>
 * 历史:	 <br>
 */
package app.data;

import app.model.User;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DataCache负责管理缓存数据.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class DataCache {
    
    /** 单例私有静态对象*/
    private static DataCache dataCache = null;
    /** 读取缓存*/
    private static ConcurrentHashMap<String, User> readCache = null;
    /** 写入缓存*/
    private static ConcurrentHashMap<String, User> writeCache = null;
    /** 磁盘读写对象*/
    private static DataDisk dataDisk = null;
    
    /**
     * 实例化DataCache并返回
     */
    public static synchronized DataCache getInstance() {
        if (dataCache == null) {
            dataCache = new DataCache();
        }
        return dataCache;
    }
    
    /**
     * DataCache构造函数，初始化私有静态变量
     */
    private DataCache() {
        readCache = new ConcurrentHashMap<String, User>();
        writeCache = new ConcurrentHashMap<String, User>();
        dataDisk = DataDisk.getInstance();
    }
    
    /**
     * 在readCache，writeCache或dataDisk中查找用户
     * 
     * @param [account]---查询函数所需要的关键字字符串account
     * @return [User] 返回缓存中或者磁盘文件中查询到的User
     * @throws [IOException] 文件读取异常
     */
    public synchronized User query(String account) throws IOException {
        User user = null;
        
        /*先在readCache或者writeCache查找是否存在*/
        if (readCache.containsKey(account) ) {
            return readCache.get(account);
        } else if (writeCache.containsKey(account)) {
            return writeCache.get(account);
        }
        
        /*如果缓存中没有，在磁盘文件中查找*/
        user = dataDisk.query(account);                                         
        
        if (user == null) {
            return null;
        } else {
            readCache.put(account, user);
            return user;
        }
    
    }
    
    /**
     * 将注册用户插入到缓存writeCache中
     * 
     * @param [user]---将要插入到数据库的用户对象
     * @throws [IOException] 文件读取异常
     */
    public void insert(User user) throws IOException {
        /*将插入信息先写进writeCache里面*/
        writeCache.put(user.getAccount(), user);
        
        /*如果writeCache的数量大于500，*/
        if (writeCache.size() >= 500) {
            readCache.putAll(writeCache);
            dataDisk.writeToDisk(writeCache);
        } else {
            readCache.put(user.getAccount(), user);                             /*将刚写入的用户信息写入到readCache中*/
        }
                 
    }
    
    
    /**
     * 根据用户account修改用密码
     * 
     * @param [account]---修改密码的关键字account
     * @param [password]---修改密码的新密码password
     * @throws [IOException] 文件读取异常
     */
    public void edit(String account, String password) throws IOException {
        User user = query(account);
        user.setPassword(password);
        writeCache.put(account, user);                                          /*将修改后的用户信息存进俩个缓存中*/
        readCache.put(account, user);
    }
    
    
    /**
     * 删除readCache中相应的值
     * 
     * @param [account]---删除函数所需要的关键字字符串account
     * @throws [IOException] 文件读取异常
     */
    public void remove(String account) throws IOException {
        
        /*如果writeCache中还有对象，将其全部写进磁盘文件中*/
        if (writeCache.size() > 0) {
            readCache.putAll(writeCache);
            dataDisk.writeToDisk(writeCache);
        }
        
        /*在readCache中也将其删除*/
         readCache.remove(account);
    }
    
}
