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
public class RegisterController extends HttpServlet {

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
            url = "/register.jsp";
        } else {
            url = "/info.jsp";
            request.setAttribute("warn", "Logout to sign up");
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
        
        Database database = Database.getInstance();
        
        boolean status = false;
        
        String url = "/register.jsp";
        
        if (database.queryIfExist(account)) {
            request.setAttribute("warn", "account is exist");
            
        } else if (password.length() != 8){
            request.setAttribute("warn", "password too long!");
            
        } else {
            database.insert(account, password, username);
            request.setAttribute("warn", "success, you can login now");
            url = "/index.jsp";
        }
//        System.out.println("app.controllers.RegisterController.doPost()"
//                +username+password);
//        database.insert(username, password, password);
//        database.edit(username, password);
          
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
    }

    
}
