/**
 * 负责数据库文件信息的增删改查的DataBase
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.data;

import app.model.User;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

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
