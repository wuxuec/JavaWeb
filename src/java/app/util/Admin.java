/*
* 模块:   功能层 <br>
* 用途:   管理在线人数 <br>
* 作者:   武家辉 wujh6@asiainfo.com <br>
* 日期:   2016.09.05 <br>
* 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
* 版本:   RCS: $Id$ <br>
* 说明:	 <br>
* 历史:	 <br>
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
