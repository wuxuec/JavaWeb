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


public class DataBase {
    
    private static DataBase database = null;
    private static DataCache dataCache = null;
  
    
    //单例模式，只实例化一个静态对象提供操作
    public synchronized static DataBase getInstance() {
            
        if (database == null) {
            database = new DataBase();
        }
        
        return database;
    }
    
    //构造函数，打开数据文件，如果不存在则直接创建
    private DataBase() {

        dataCache = DataCache.getInstance();
      
    }
    
    public User query(String account) throws IOException {
        User user = dataCache.query(account);
        return user;
    }
    
   
    
    //将新的账户信息插入到数据文件中
    public  void insert(String account,
            String password, String username) throws IOException {
        
        User user = new User(account, password, username);
        dataCache.insert(user);
        
    }
    
    //根据新旧密码来更新数据文件中的密码信息
    public void edit(String account, String newPassword)
            throws IOException{
        
        dataCache.edit(account, newPassword);
    }
    
}
