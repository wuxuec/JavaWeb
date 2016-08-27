/**
 * 负责Cookie查询取值的CookieUtil
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class CookieUtil {
    
    public boolean ifHasLogin(HttpServletRequest request) {
        boolean result = false;
        
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            return false;
        } 
        
        String cookieName = "userCookie";
        String cookieValue = getCookieValueByName(request, cookieName);
        
        if (cookieValue != null) {
            result = true;
        }
        
        return result;
    }
    
    public String getCookieValueByName(HttpServletRequest request,
            String name) {
        
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                System.out.println(cookie.getName());
                if (name.equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                    break;
                } 
            }
        } 
        
        return cookieValue;
    }
    
}
