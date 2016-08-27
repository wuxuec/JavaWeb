/**
 * 监听会话被销毁的的UserSessionListener
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class UserSessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
        HttpSession session = se.getSession();
//        System.out.println("This session is been destroyed: "+session.getId());
        
        if (session.getAttribute("account") != null) {
            Admin.getInstance().downOnlineNumber();
        }
    }
    
}
