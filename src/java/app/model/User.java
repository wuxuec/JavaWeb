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
 * User负责提供用户模型.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class User {
    
    /** 账户字符串*/
    private String account = "";
    /** 密码字符串*/
    private String password = "";
    /** 用户名字符串*/
    private String username = "";
    
   /**
     * User的公有构造函数，初始化私有变量
     * 
     * @param [account]---初始化account的值
     * @param [password]---初始化password的值
     * @param [username]---初始化username的值
     */
    public User(String account, String password, String username) {
        this.account = account;
        this.password = password;
        this.username = username;
    }

    /**
     * 获取account的值
     * 
     * @return [String] 返回account的值
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置account的值
     * 
     * @param [account]---设置account的值为account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取password的值
     * 
     * @return [String] 返回password的值
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password的值
     * 
     * @param [account]---设置password的值为password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取username的值
     * 
     * @return [String] 返回username的值
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置username的值
     * 
     * @param [account]---设置username的值为username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
}
