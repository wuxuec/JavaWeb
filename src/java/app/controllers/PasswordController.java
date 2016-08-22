/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
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
        
        String url = "/password.jsp";
        
        HttpSession httpSession = request.getSession();
        String message = (String)httpSession.getAttribute("message");
        
        request.setAttribute("message", message);
        
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String origin = request.getParameter("origin");
        String newPw = request.getParameter("new");
        
        String url = "/info.jsp";
        
        request.setAttribute("username", origin);
        request.setAttribute("password", newPw);
                
        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
        
    }

    
}
