/**
 * 管理在线人数的管理员Admin
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.util;

public class Admin {
    
    private static Admin admin = null;
    private static int onlineNumber;
    
    public static synchronized Admin getInstance() {
        if (admin == null) {
            admin = new Admin();
        }
        return admin;
    }
    
    public Admin() {
        onlineNumber = 0;
    }
    
    public void addOnlineNumber() {
        onlineNumber++;
    }
    
    public void downOnlineNumber() {
        onlineNumber--;
        if (onlineNumber < 0) {
            onlineNumber = 0;
        }
    }
    
    public int getOnlineNumber() {
        return onlineNumber;
    }
    
}
