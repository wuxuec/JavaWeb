/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author WuJiahui
 */
public class Database {
    
    private static Database database = null;
    private static File file = null;
    
    public synchronized static Database getInstance
        () {
            
        if (database == null) {
            database = new Database();
        }
        
        return database;
    }
    
    private Database() {
//        String path = servletContext.getRealPath("/");
        String path = this.getClass().getClassLoader().getResource("/")
                .getPath() + "information.txt";
        //file =  new File(path+"\\data\\information.txt");
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("app.data.Database.<init>()"+path); //test
    }
    
    public boolean queryIfExist(String account) throws IOException{
        
        boolean result = false;	
	RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
	String record = null;

	while ((record = infoFile.readLine()) != null) {
            String[] info = record.toString().split("\\|");
//            System.out.println(info[0]);
            if (info[0].equals(account)) {
                infoFile.close();
                return true;
            }
            
        }
        
        //System.out.println(account);
        
	infoFile.close();
        return result;
    }
    
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
    
    public void insert(String account, String password, String username)
            throws IOException{
        
        RandomAccessFile infoFile = new RandomAccessFile(file, "rw");
        String record;
        long offset = 0;
        while ((record = infoFile.readLine()) != null) {
            offset += record.getBytes().length+2;
        }
        infoFile.seek(offset);
//        infoFile.writeBytes(String.valueOf(i)+"|name|account|correct!\r\n");
        record = account+"|"+password+"|"+username+"\r\n";
//        System.out.println("app.data.Database.insert()"+record);
        
        infoFile.writeBytes(record);
        infoFile.close();
        
    }
    
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
