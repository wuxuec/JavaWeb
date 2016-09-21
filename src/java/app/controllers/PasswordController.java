/*
 * 模块:   控制层servlet <br>
 * 用途:   处理修改密码请求 <br>
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
 * OnlineController处理查询在线人数请求并返回请求结果.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class PasswordController extends HttpServlet {
    
    /**
     * 处理/pwd路径下的GET请求并根据登录状态返回对应页面
     *
     * @param [request]---GET方法中的HttpServletRequest
     * @param [response]---GET方法中的HttpServletResponse
     * @throws [ServletException] Servlet异常
     * @throws [IOException] 文件读写异常
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String url = "/password.jsp";
        CookieUtil cookieUtil = new CookieUtil();
        boolean isLogin = cookieUtil.ifHasLogin(request);
        
        if (!isLogin) {
            url = "/index.jsp";
            request.setAttribute("warn", "You have to login again");
        }

        getServletContext().getRequestDispatcher(url)
                .forward(request, response);

    }

    /**
     * 处理/pwd路径下的POST请求并修改该用户密码
     *
     * @param [request]---POST方法中的HttpServletRequest
     * @param [response]---POST方法中的HttpServletResponse
     * @throws [ServletException] Servlet异常
     * @throws [IOException] 文件读写异常
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String oldPassword = request.getParameter("old");
        String newPassword = request.getParameter("new");
        String url = "/password.jsp";
        DataBase database = DataBase.getInstance();
        CookieUtil cookieUtil = new CookieUtil();
        String cookieName = "userCookie";
        String account = (String) request.getSession().getAttribute("account"); /*在session回去account信息*/

        if (account == null) {                                                  /*如果session处于未登录状态，则account为空*/
            url = "/index.jsp";
            request.setAttribute("warn",
                    "Maybe it's time out, please login again!");
        } else {
            User user = database.query(account);

            if (user.getPassword().equals(oldPassword)) {                       /*验证原密码是否正确*/
                database.edit(account, newPassword);
                url = "/info.jsp";
                request.setAttribute("warn", "Success to edit the password");
            } else {
                url = "/password.jsp";
                request.setAttribute("warn", "old password is wrong.");
            }
        }

        getServletContext().getRequestDispatcher(url)
                .forward(request, response);

    }

}
