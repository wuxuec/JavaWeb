/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.data.Database;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author WuJiahui
 */
public class PasswordController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        Cookie[] cookies = request.getCookies();
        String cookieName = "userCookie";
        String cookieValue = "";
        for (Cookie cookie: cookies) {
            if (cookieName.equals(cookie.getName()))
                cookieValue = cookie.getValue();
        }
        
        String url = "/password.jsp";
        if (cookieValue.equals("")) {
            url = "/index.jsp";
            request.setAttribute("warn", "Please login again!");
        } 
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String origin = request.getParameter("origin");
        String newPassword = request.getParameter("new");
        
        Cookie[] cookies = request.getCookies();
        String cookieName = "userCookie";
        String account = "";
        for (Cookie cookie: cookies) {
            if (cookieName.equals(cookie.getName()))
                account = cookie.getValue();
        }
        
        String url = "/password.jsp";
        Database database = Database.getInstance();
        if (account.equals("")) {
            url = "/index.jsp";
            request.setAttribute("warn", "Please login again!");
        } else {
            if (database.verify(account, origin)) {
                database.edit(account, newPassword);
                url = "/info.jsp";
                request.setAttribute("warn", "Success edit the password");
            } else {
                url = "/password.jsp";
                request.setAttribute("warn", "original password is wrong.");
            }
        }
            
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

    
}
