/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    
    public User query(String account) throws IOException {
        
        if (readCache.contains(account) ) {
            return readCache.get(account);
        } else if (writeCache.contains(account)) {
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
        
        writeCache.put(user.getAccount(), user);
        
        if (writeCache.size() >= 150) {
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
    
    
    
    
}
