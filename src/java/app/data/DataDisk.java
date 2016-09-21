/*
 * 模块:   数据层 <br>
 * 用途:   负责读取磁盘数据和将缓存数据写入磁盘文件中 <br>
 * 作者:   武家辉 wujh6@asiainfo.com <br>
 * 日期:   2016.09.05 <br>
 * 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
 * 版本:   RCS: $Id$ <br>
 * 说明:	 <br>
 * 历史:	 <br>
 */
package app.data;

import app.model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DataDisk负责读取磁盘数据和将缓存数据写入磁盘文件中.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class DataDisk {

    /** 单例私有静态对象*/
    private static DataDisk dataDisk = null;
    /** 用户信息文件对象*/
    private static File file = null;
    
    /**
     * 获取DataCache的实例对象
     */
    public static synchronized DataDisk getInstance() {
        if (dataDisk == null) {
            dataDisk = new DataDisk();
        }
        return dataDisk;
    }
    
    /**
     * DataDisk的私有构造函数
     */
    private DataDisk() {
       String path = this.getClass().getClassLoader().getResource("/")
                .getPath() + "information.txt";                                 /*获取文件的绝对路径*/
        file = new File(path);
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * 在文件中查找是否有该account的用户信息
     * 
     * @param [account]---查询函数所需要的关键字字符串account
     * @return [User] 返回文件中查询到的User
     * @throws [IOException] 文件读取异常
     */
    public synchronized User query(String account) throws IOException{
        RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
        String record = null;
        
        /*若文件为空，则关闭文件流并返回*/
        if (infoFile.length() == 0) {
            infoFile.close();
            return null;
        }
        
	while ((record = infoFile.readLine()) != null) {
            String[] info = record.toString().split("\\|");
            if (info[0].equals(account)) {                                      /*info[0]为用户的account字符串*/
                infoFile.close();
                return parseRecordToUser(record);                               /*若找到，直接返回*/
            }
        }
        
	infoFile.close();
        
        return null;                                                            /*找不到则返回空*/
    }
    
    
    /**
     * 将文件中的记录record转换成用户对象User
     * 
     * @param [record]---文件中的一条String类型信息记录
     * @return [User] 转换后的User
     */
    private User parseRecordToUser(String record) {
        
        String[] info = record.toString().split("\\|");
        User user = new User(info[0], info[1], info[2]);                        /*info的三个成员依次为account，password，username*/
        return user;
    }
    
    /**
     * 将用户对象User转换为信息文件的字符创格式
     * 
     * @param [user]---进行转换的用户对象User
     * @return [String] 转换后的字符串String
     */
    private String convertUserToRecord(User user) {
        String record = user.getAccount()+"|"
                        +user.getPassword()+"|"+user.getUsername()+"\r\n";
        return record;
    }
    
    /**
     * 将writeCache中的信息写入到磁盘文件中
     * 
     * @param [writeCache]---缓存wirteCache
     * @throws [IOException] 文件读取异常
     * @throws [FileNotFoundException] 文件查找异常
     */
    public synchronized void writeToDisk(
            ConcurrentHashMap<String, User> writeCache)
            throws FileNotFoundException, IOException {
        
        /*若缓存为空，直接返回，不进行操作*/
        if (writeCache.size() == 0) {
            return;
        }
        
        RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
        String record;
        long offset = 0;
        
        while ((record = infoFile.readLine()) != null) {
            String[] info = record.split("\\|");
            
            /*如果writeCache中有文件中的用户信息，则将writeCache中的信息写入文件中*/
            if (writeCache.containsKey(info[0])) {
                infoFile.seek(offset);
                User user = writeCache.get(info[0]);
                record = convertUserToRecord(user);
                infoFile.seek(offset);
                infoFile.writeBytes(record);
                
                offset += record.getBytes().length;                             /*根据offset进行定位，准备写入*/
                
                writeCache.remove(info[0]);                                     /*写完后删除writeCache中的相应的对象*/
                
            } else {
                offset += record.getBytes().length+2;
            }
            
        }
        
        infoFile.seek(offset);
        
        Iterator<Map.Entry<String, User>> iterator = writeCache
                .entrySet().iterator();
        
        /*writeCache中剩下的值都为文件中不存在的用户，直接迭代循环把全部信息写进文件*/
        while (iterator.hasNext()) {
            record = convertUserToRecord(iterator.next().getValue());
            infoFile.write(record.getBytes());
        }
        
        writeCache.clear();
        infoFile.close();
        
    }
    
}
