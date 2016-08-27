/**
 * 管理网站登陆和推出的操作的Servlet
 *
 * @version 	1.0
 * @author 	武家辉
 */

package app.controllers;

import app.util.Admin;
import app.data.Database;
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
import javax.smartcardio.ResponseAPDU;


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

        String url = "/info.jsp";
        
        String action = request.getParameter("action");
        if (action.equals("login")) {
            boolean success = login(request, response);
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
        Database database = Database.getInstance();
            
        if (database.queryIfExist(account)) {
            success = database.verify(account, password);
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
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String message = dateFormat.format(date);
        
        String username = database.queryUserName(account);
                
        httpSession.setAttribute("account", account);
        httpSession.setAttribute("username", username);
        httpSession.setAttribute("message", message);
                
        Cookie cookie = new Cookie("userCookie", account);
        cookie.setMaxAge(60*5);
        cookie.setPath("/");
        response.addCookie(cookie);
                
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
