/**
 * 处理用户修改密码的请求的Servlet
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.controllers;

import app.data.Database;
import app.util.CookieUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class PasswordController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String origin = request.getParameter("origin");
        String newPassword = request.getParameter("new");
        String url = "/password.jsp";
        Database database = Database.getInstance();
        
        CookieUtil cookieUtil = new CookieUtil();
        String cookieName = "userCookie";
//        String account = cookieUtil.getCookieValueByName(request, cookieName);
        String account = (String)request.getSession().getAttribute("account");
        
        if (account == null) {
            url = "/index.jsp";
            request.setAttribute("warn", "Maybe it's time out, please login again!");
        } else {
            if (database.verify(account, origin)) {
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
