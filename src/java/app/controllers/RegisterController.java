/*
* 模块:   控制层servlet <br>
* 用途:   处理注册请求 <br>
* 作者:   武家辉 wujh6@asiainfo.com <br>
* 日期:   2016.08.16 <br>
* 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
* 版本:   RCS: $Id$ <br>
* 说明:	 <br>
* 历史:	武家辉 2016.09.15 完善功能，增加注释 <br>
*/

package app.controllers;

import app.data.DataBase;
import app.model.User;
import app.util.CookieUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/register.jsp";
        
        CookieUtil cookieUtil = new CookieUtil();
        boolean isLogin = cookieUtil.ifHasLogin(request);
        if (isLogin) {
            url = "/info.jsp";
            request.setAttribute("warn", "You can't register after you login");
        }
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String account = request.getParameter("account");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String url = "/register.jsp";
        
        if (account == null || username == null || password == null) {
            request.setAttribute("warn", "unvalid input");
            getServletContext().getRequestDispatcher(url)
                .forward(request, response);
            return;
        }
        
        DataBase database = DataBase.getInstance(); 
        User user = database.query(account);
        if (user != null) {
            request.setAttribute("warn", "this account name has been sign up");
        } else if (password.length() != 8){
            request.setAttribute("warn", "password has to be 8 char!");
        } else {
            database.insert(account, password, username);
            request.setAttribute("warn", "success! you can login now");
            url = "/index.jsp";
        }
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
    }

    
}
