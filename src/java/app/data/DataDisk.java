/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Hp
 */
public class DataDisk {

    
    private static DataDisk dataDisk = null;
     private static File file = null;
    
    public static synchronized DataDisk getInstance() {
        if (dataDisk == null) {
            dataDisk = new DataDisk();
        }
        return dataDisk;
    }
    
    private DataDisk() {
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
    
    public User query(String account) throws IOException{
        
        RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
        
        if (infoFile.length() == 0) {
            infoFile.close();
            return null;
        }
        
        String record = null;

	while ((record = infoFile.readLine()) != null) {
            String[] info = record.toString().split("\\|");
            if (info[0].equals(account)) {
                infoFile.close();
                return parseRecordToUser(record);
            }
        }
        
	infoFile.close();
        
        return null;
    }
    
    private User parseRecordToUser(String record) {
        
        String[] info = record.toString().split("\\|");
        User user = new User(info[0], info[1], info[2]);
        return user;
    }
    
    private String convertUserToRecord(User user) {
        String record = user.getAccount()+"|"
                        +user.getPassword()+"|"+user.getUsername()+"\r\n";
        return record;
    }
    
    public void writeToDisk(ConcurrentHashMap<String, User> writeCache)
            throws FileNotFoundException, IOException {
        
        RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
        String record;
        long offset = 0;
        
        while ((record = infoFile.readLine()) != null) {
            
            String[] info = record.split("\\|");
            
            if (writeCache.containsKey(info[0])) {
                
                infoFile.seek(offset);
                User user = writeCache.get(info[0]);
                record = convertUserToRecord(user);
                infoFile.seek(offset);
                infoFile.writeBytes(record);
                
                offset += record.getBytes().length;
                
                writeCache.remove(info[0]);
                
            } else {
                offset += record.getBytes().length+2;
            }
            
        }
        
        infoFile.seek(offset);
        
        Iterator<Map.Entry<String, User>> iterator = writeCache
                .entrySet().iterator();
        
        while (iterator.hasNext()) {
            record = convertUserToRecord(iterator.next().getValue());
            infoFile.write(record.getBytes());
        }
        
        writeCache.clear();
        infoFile.close();
        
    }
    
}