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


public class LoginController extends HttpServlet {

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

    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        //System.err.println("Login post");

        String url = "/info.jsp";
        
        String action = request.getParameter("action");
        if (action.equals("login")) {
            CookieUtil cookieUtil = new CookieUtil();
            boolean success = cookieUtil.ifHasLogin(request);
            if (!success) {
                success = login(request, response);
                
            }
            
            if (!success) {
                url = "/index.jsp";
            }
        } else if (action.equals("logout")) {
             url = "/index.jsp";
             logout(request, response); 
        }
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }
    
    public boolean login(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        boolean success = true;
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        DataBase database = DataBase.getInstance();
        
        User user = database.query(account);
            
        if (user != null) {
            success = user.getPassword().equals(password);
            
            if (!success) {
                request.setAttribute("warn",
                        "password wrong, password have to be 8 char");
                return false;
            }
        } else {
            request.setAttribute("warn", "Not exist!");
            return false;
        }
        
        HttpSession httpSession = request.getSession();
        Date date = new Date();
        SimpleDateFormat dateFormat = 
                new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String message = dateFormat.format(date);
        
        String username = user.getUsername();
                
        httpSession.setAttribute("account", account);
        httpSession.setAttribute("username", username);
        httpSession.setAttribute("message", message);
                
        Cookie cookie = new Cookie("userCookie", account);
        Cookie pwdCookie = new Cookie("pwdCookie", password);
        cookie.setMaxAge(60*5);
        pwdCookie.setMaxAge(60*5);
        cookie.setPath("/");
        pwdCookie.setPath("/");
        response.addCookie(cookie);
        response.addCookie(pwdCookie);
                
        Admin.getInstance().addOnlineNumber();
           
        return success;
    }
    
    public void logout(HttpServletRequest request,
            HttpServletResponse response) {
        
        request.setAttribute("warn", "You had logout, please login agian.");
            
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        
    }

}
