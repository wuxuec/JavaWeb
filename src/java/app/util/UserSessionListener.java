/**
 * 监听会话被销毁的的UserSessionListener
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.util;

import app.data.DataCache;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class UserSessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
        synchronized(this) {
            HttpSession session = se.getSession();
            String account = (String)session.getAttribute("account");
            if (account != null) {
                try {
                    Admin.getInstance().downOnlineNumber();
                    DataCache.getInstance().remove(account);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        
    }
    
}
