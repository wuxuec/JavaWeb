/**
 * 处理注册请求的Servlet
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
        
        Database database = Database.getInstance(); 
        if (database.queryIfExist(account)) {
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
