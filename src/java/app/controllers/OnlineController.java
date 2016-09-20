/*
* 模块:   控制层servlet <br>
* 用途:   处理查询在线人数请求 <br>
* 作者:   武家辉 wujh6@asiainfo.com <br>
* 日期:   2016.08.16 <br>
* 版权:   Copyright (c) 2004-2008 AsiaInfo Technologies(China),Inc. <br>
* 版本:   RCS: $Id$ <br>
* 说明:	 <br>
* 历史:	武家辉 2016.09.15 增加注释 <br>
*/

package app.controllers;

import app.util.Admin;
import java.io.IOException;
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
//        System.out.println(String.valueOf(num)+" is from "
//                +req.getSession().getId());
        resp.setContentType("text");
        resp.getWriter().write(String.valueOf(num));
        
    }

}
