/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.data.Database;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.smartcardio.ResponseAPDU;

/**
 *
 * @author WuJiahui
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        Cookie[] cookies = request.getCookies();
        String cookieName = "userCookie";
        String cookieValue = "";
        for (Cookie cookie: cookies) {
            if (cookieName.equals(cookie.getName()))
                cookieValue = cookie.getValue();
        }
        
        String url = "";
        if (cookieValue.equals("")) {
            url = "/index.jsp";
            request.setAttribute("warn", "Please login !");
        } else {
            url = "/info.jsp";
        }
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        String url = "/info.jsp";
        
        if (action.equals("login")) {
            
            
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            Database database = Database.getInstance();
            boolean status = false;
            
            if (database.queryIfExist(account)) {
                status = database.verify(account, password);
                if (!status) 
                     request.setAttribute("warn", "password wrong");
                
            } else {
                
//                System.out.println("not exist ? "+account);
                 request.setAttribute("warn", "Not exist!");
            }
            
            if (status) {
                
                HttpSession httpSession = request.getSession();
                Date date = new Date();
                String message = date.toString();
                String username = database.queryUserName(account);
                
                httpSession.setAttribute("account", account);
                httpSession.setAttribute("username", username);
                httpSession.setAttribute("message", message);
                
                Cookie cookie = new Cookie("userCookie", account);
                cookie.setMaxAge(60*5);
                cookie.setPath("/");
                response.addCookie(cookie);
                
                url = "/info.jsp";
                
            } else {
                url = "/index.jsp";
               
            }
            
            
        } else if (action.equals("logout")) {
            url = "/index.jsp";
            
            request.setAttribute("warn", "please login again.");
            
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie: cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            
            HttpSession httpSession = request.getSession();
            httpSession.invalidate();
            
        }
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

}
