/**
 * 处理查询在线总人数请求的Servlet
 *
 * @version 	1.0
 * @author 	武家辉
 */
package app.controllers;

import app.util.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OnlineController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        
        int num = Admin.getInstance().getOnlineNumber();
        
        resp.setContentType("text");
        resp.getWriter().write(String.valueOf(num));
        
    }
    
    

   

    
}
