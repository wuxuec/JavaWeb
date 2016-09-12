/**
 * 负责数据库文件信息的增删改查的DataBase
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class Database {
    
    private static Database database = null;
    private static File file = null;
    
    //单例模式，只实例化一个静态对象提供操作
    public synchronized static Database getInstance() {
            
        if (database == null) {
            database = new Database();
        }
        
        return database;
    }
    
    //构造函数，打开数据文件，如果不存在则直接创建
    private Database() {

        String path = this.getClass().getClassLoader().getResource("/")
                .getPath() + "information.txt";
        file = new File(path);
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
      
    }
    
    //根据账户名称来查找文件中是否已存在此账户
    public boolean queryIfExist(String account) throws IOException{
        
        boolean result = false;	
	RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
	String record = null;

	while ((record = infoFile.readLine()) != null) {
            String[] info = record.toString().split("\\|");
            if (info[0].equals(account)) {
                infoFile.close();
                return true;
            }
        }
        
	infoFile.close();
        
        return result;
    }
    
    //根据账户来查找该账户的账户的名称
    public String queryUserName(String account) throws IOException{
        
        String username = "";
        RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
	String record = null;

	while ((record = infoFile.readLine()) != null) {
            String[] info = record.split("\\|");
            if (info[0].equals(account)) {
                username = info[2];
                break;
            }
        }
		
	infoFile.close();
        
        return username;
        
    }
        
    //根据账户和密码来判断是否通过验证，返回一个boolean值
    public boolean verify(String account, String password)
            throws IOException {
        
        boolean result = false;	
	RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
	String record = null;

	while ((record = infoFile.readLine()) != null) {
            String[] info = record.split("\\|");
            if (info[0].equals(account)) {
                if (info[1].equals(password)) {
                    infoFile.close();
                    return true;
                } else {
                    infoFile.close();
                    return false;
                }
            }
        }
		
	infoFile.close();
        
        return result;
    }
    
    //将新的账户信息插入到数据文件中
    public synchronized void insert(String account, String password, String username) {
        
        RandomAccessFile infoFile = null;
        
        try {
            infoFile = new RandomAccessFile(file, "rw");
            String record;
            long offset = infoFile.length();
//            System.out.println("app.data.Database.insert() off : "+ off);
//            System.out.println("app.data.Database.insert()offset : "+ offset);
            
            infoFile.seek(offset);
            record = account+"|"+password+"|"+username+"\r\n";
            infoFile.write(record.getBytes());
            infoFile.close();
            
        } catch (IOException e) {
            e.printStackTrace();
            
        } finally {
            if (infoFile != null) {
                try {
                    infoFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        
    }
    
    //根据新旧密码来更新数据文件中的密码信息
    public void edit(String account, String newPassword)
            throws IOException{
        
        RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
        String record;
        long offset = 0;
        
        while ((record = infoFile.readLine()) != null) {
            String[] info = record.split("\\|");
            if (info[0].equals(account)) {
                infoFile.seek(offset);
                infoFile.writeBytes(info[0]+"|"+newPassword+"|"+info[2]+"\r\n");
                break;
            } else {
                 offset += record.getBytes().length+2;
            }
        }
        
        infoFile.close();
    }
    
}
