/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author WuJiahui
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/index.jsp";
        
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
            String username = request.getParameter("username");
            String password = request.getParameter("password");
        
            url = "/info.jsp";
        
            request.setAttribute("username", username);
            request.setAttribute("password" , password);
        } else if (action.equals("logout")) {
            url = "/info.jsp";
            
            String username = "you hava log out";
            String password = "you hava log out";
        
            request.setAttribute("username", username);
            request.setAttribute("password" , password);
        }
        
        
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

}
