/*
 * 模块:   功能层 <br>
 * 用途:   监听session的状态 <br>
 * 作者:   武家辉 wujh6@asiainfo.com <br>
 * 日期:   2016.08.18 <br>
 * 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
 * 版本:   RCS: $Id$ <br>
 * 说明:	 <br>
 * 历史:	 武家辉 2016.09.15 完善功能，增加注释<br>
 */
package app.util;

import app.data.DataCache;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * CookieUtil负责监听session的状态.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class UserSessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {}

    /**
     * 监听session销毁的回调函数
     * 
     * @param [se]---session事件
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
        synchronized(this) {
            HttpSession session = se.getSession();
            String account = (String)session.getAttribute("account");
            
            if (account != null) {
                try {
                    Admin.getInstance().downOnlineNumber();                     /*在线人数相应减1*/
                    DataCache.getInstance().remove(account);                    /*删除缓存中的该对象*/
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        
    }
    
}
