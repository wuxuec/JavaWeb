/*
 * 模块:   功能层 <br>
 * 用途:   处理Cookie的查询 <br>
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

/**
 * CookieUtil负责处理Cookie的查询.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class CookieUtil {
    
    /**
     * 根据request中的session检查用户是否已经登录
     * 
     * @param [request]---用于检验的HttpServletRequest
     * @return [boolean] 返回的登陆状态
     */
    public boolean ifHasLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        /*如果session中存在account信息，则表示已登陆*/
        if (session.getAttribute("account") == null) {
            return false;
        } 
        
        return true;
    }
    
    /**
     * 检查request中是否已经存在cookie
     * 
     * @param [request]---用于检验的HttpServletRequest
     * @return [boolean] cookie是否存在
     */
    public boolean ifCookiesExist(HttpServletRequest request) {
        String cookieName = "userCookie";
        String cookieValue = getCookieValueByName(request, cookieName);
        
        /*如果userCookie不为空，则cookie存在*/
        if (cookieValue != null) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 根据字符串查询其在Cookie中相对应的值
     * 
     * @param [request]---用于检验的HttpServletRequest
     * @param [name]---查询字符串的值
     * @return [String] cookie中的值，可能为空
     */
    public String getCookieValueByName(HttpServletRequest request,
            String name) {
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (name.equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                    break;
                } 
            }
        } 
        
        return cookieValue;
    }
    
}
