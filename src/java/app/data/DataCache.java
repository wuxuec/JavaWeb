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
 *
 * @author Hp
 */
public class DataCache {
    
    private static DataCache dataCache = null;
    private static ConcurrentHashMap<String, User> readCache = null;
    private static ConcurrentHashMap<String, User> writeCache = null;
    private static DataDisk dataDisk = null;
    
    public static synchronized DataCache getInstance() {
        if (dataCache == null) {
            dataCache = new DataCache();
        }
        return dataCache;
    }
    
    private DataCache() {
        readCache = new ConcurrentHashMap<String, User>();
        writeCache = new ConcurrentHashMap<String, User>();
        dataDisk = DataDisk.getInstance();
    }
    
    
    public synchronized User query(String account) throws IOException {
        
        if (readCache.containsKey(account) ) {
            return readCache.get(account);
        } else if (writeCache.containsKey(account)) {
            return writeCache.get(account);
        }
        
        User user = dataDisk.query(account);
        
        if (user == null) {
            return null;
        } else {
            readCache.put(account, user);
            return user;
        }
    
    }
    
    public void insert(User user) throws IOException {
//        System.out.println(writeCache.size()); //test
        
        writeCache.put(user.getAccount(), user);
        
        if (writeCache.size() >= 500) {
            readCache.putAll(writeCache);
            dataDisk.writeToDisk(writeCache);
            
        } else {
            readCache.put(user.getAccount(), user);
            
        }
                 
    }
    
    public void edit(String account, String password) throws IOException {
        
        User user = query(account);
        user.setPassword(password);
        writeCache.put(account, user);
        readCache.put(account, user);
        
    }
    
    public void remove(String account) throws IOException {
        
        if (writeCache.size() > 0) {
            readCache.putAll(writeCache);
            dataDisk.writeToDisk(writeCache);
        }
        
         readCache.remove(account);
        
    }
    
    
}
