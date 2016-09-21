/*
 * 模块:   控制层servlet <br>
 * 用途:   处理登录请求 <br>
 * 作者:   武家辉 wujh6@asiainfo.com <br>
 * 日期:   2016.08.16 <br>
 * 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
 * 版本:   RCS: $Id$ <br>
 * 说明:	 <br>
 * 历史:	武家辉 2016.09.15 完善功能，修改验证逻辑 <br>
 */

package app.controllers;

import app.util.Admin;
import app.data.DataBase;
import app.model.User;
import app.util.CookieUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginController处理登录请求并返回请求结果.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class LoginController extends HttpServlet {
    
    /**
     * 处理/login路径下的GET请求并根据登录状态返回对应页面
     *
     * @param [request]---GET方法中的HttpServletRequest
     * @param [response]---GET方法中的HttpServletResponse
     * @throws [ServletException] Servlet异常
     * @throws [IOException] 文件读写异常
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        CookieUtil cookieUtil = new CookieUtil();
        boolean isLogin = cookieUtil.ifHasLogin(request);
        String url = "/info.jsp";
        
        if (!isLogin) {
            url = "/index.jsp";
            request.setAttribute("warn", "You have to login again");
        } 
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
    }
    
    /**
     * 处理/login路径下的POST请求并根据登录状态返回对应页面
     *
     * @param [request]---POST方法中的HttpServletRequest
     * @param [response]---POST方法中的HttpServletResponse
     * @throws [ServletException] Servlet异常
     * @throws [IOException] 文件读写异常
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/info.jsp";
        String action = request.getParameter("action");                         /*action可以是登录login或者登出logout*/
        
        if (action.equals("login")) {
            CookieUtil cookieUtil = new CookieUtil();
            boolean success = cookieUtil.ifHasLogin(request);
            
            /*如果还没登录，则进行登录*/
            if (!success) {
                success = login(request, response);                             /*success值设为能否登陆成功*/
            }
            
            if (!success) {
                url = "/index.jsp";                                             /*登录失败，则返回界面设置为主页*/
            }
        } else if (action.equals("logout")) {
             url = "/index.jsp";
             logout(request, response); 
        }
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }
    
    /**
     * 登录函数利用请求中的信息进行尝试登录
     *
     * @param [request]---POST方法中的HttpServletRequest
     * @param [response]---POST方法中的HttpServletResponse
     * @return [boolean] 是否登陆成功
     * @throws [IOException] 文件读写异常
     */
    public boolean login(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        boolean success = true;
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        DataBase database = DataBase.getInstance();
        User user = database.query(account);                                    /*根据账户名查找改账户*/
        HttpSession httpSession = request.getSession();
        Date date = new Date();
        SimpleDateFormat dateFormat = 
                new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String message = dateFormat.format(date);
        String username = "";
            
        /*如果能在数据库中查找到改用户，则user不为空*/
        if (user != null) {                                             
            success = user.getPassword().equals(password);                      /*验证登录密码是否正确*/
            
            if (!success) {
                request.setAttribute("warn",
                        "password wrong, password have to be 8 char");
                return false;
            }
        } else {
            request.setAttribute("warn", "Not exist!");
            return false;
        }
        
        username = user.getUsername();
        httpSession.setAttribute("account", account);
        httpSession.setAttribute("username", username);
        httpSession.setAttribute("message", message);
        
        /*将账号和密码装进cookie中返回*/
        Cookie cookie = new Cookie("userCookie", account);
        Cookie pwdCookie = new Cookie("pwdCookie", password);
        cookie.setMaxAge(60*5);
        pwdCookie.setMaxAge(60*5);
        cookie.setPath("/");
        pwdCookie.setPath("/");
        response.addCookie(cookie);
        response.addCookie(pwdCookie);
                
        Admin.getInstance().addOnlineNumber();                                  /*登录人数加1*/
           
        return success;
    }
    
    /**
     * 登出函数
     *
     * @param [request]---POST方法中的HttpServletRequest
     * @param [response]---POST方法中的HttpServletResponse
     */
    public void logout(HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("warn", "You had logout, please login agian.");
            
        /*销毁cookies*/
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        
        /*销毁session*/
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
    }

}
