/*
* 模块:   功能层 <br>
* 用途:   管理在线人数 <br>
* 作者:   武家辉 wujh6@asiainfo.com <br>
* 日期:   2016.08.18 <br>
* 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
* 版本:   RCS: $Id$ <br>
* 说明:	 <br>
* 历史:	 武家辉 2016.09.15 完善功能，增加注释<br>
*/
package app.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class CookieUtil {
    
    public boolean ifHasLogin(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            return false;
        } 
        
        return true;
    }
    
    public boolean ifCookiesExist(HttpServletRequest request) {
        String cookieName = "userCookie";
        String cookieValue = getCookieValueByName(request, cookieName);
        
        if (cookieValue != null) {
            return true;
        }
        
        return false;
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
