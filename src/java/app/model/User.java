/*
* 模块:   模型层 <br>
* 用途:   提供用户模型 <br>
* 作者:   武家辉 wujh6@asiainfo.com <br>
* 日期:   2016.09.05 <br>
* 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
* 版本:   RCS: $Id$ <br>
* 说明:	 <br>
* 历史:	 <br>
*/
package app.model;

/**
 *
 * @author Hp
 */
public class User {
    
    private String account = "";
    private String password = "";
    private String username = "";
    
    public User(String account, String password, String username) {
        this.account = account;
        this.password = password;
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
}
