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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RegisterController处理查询在线人数请求并返回请求结果.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class RegisterController extends HttpServlet {

    /**
     * 处理/register路径下的GET请求并根据登录状态来返回响应页面
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
        String url = "/register.jsp";
        CookieUtil cookieUtil = new CookieUtil();
        boolean isLogin = cookieUtil.ifHasLogin(request);
        
        /*若已登陆，则跳转到info page*/
        if (isLogin) {
            url = "/info.jsp";
            request.setAttribute("warn", "You can't register after you login");
        }
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

    /**
     * 处理/register路径下的POST注册请求
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
        
        String account = request.getParameter("account");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String url = "/register.jsp";
        DataBase database = DataBase.getInstance(); 
        User user = null;
        
        if (account == null || username == null || password == null) {          /*判断注册信息是否合法*/
            request.setAttribute("warn", "unvalid input");
            getServletContext().getRequestDispatcher(url)
                .forward(request, response);
            return;
        }
        
        user = database.query(account);
        
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
