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
 * 处理用户修改密码的请求的Servlet.
 *
 * @version    1.0
 * @author     武家辉
 */
public class PasswordController extends HttpServlet {


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


    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String origin = request.getParameter("origin");
        String newPassword = request.getParameter("new");
        String url = "/password.jsp";
        DataBase database = DataBase.getInstance();

        CookieUtil cookieUtil = new CookieUtil();
        String cookieName = "userCookie";
        String account = (String) request.getSession().getAttribute("account");

        if (account == null) {
            url = "/index.jsp";
            request.setAttribute("warn",
                    "Maybe it's time out, please login again!");
        } else {
            User user = database.query(account);

            if (user.getPassword().equals(origin)) {
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
