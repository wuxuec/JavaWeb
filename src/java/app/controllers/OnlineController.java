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

/**
 * OnlineController处理查询在线人数请求并返回请求结果.
 *
 * @version 	2.0 
 * @author 	武家辉
 */
public class OnlineController extends HttpServlet {
    
    /**
     * 处理/online路径下的GET请求并返回在线人数
     *
     * @param [request]---GET方法中的HttpServletRequest
     * @param [response]---GET方法中的HttpServletResponse
     * @throws [ServletException] Servlet异常
     * @throws [IOException] 文件读写异常
     */
    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        
        int num = Admin.getInstance().getOnlineNumber();
        
        /*将人数信息写回到response中*/
        resp.setContentType("text");                                            
        resp.getWriter().write(String.valueOf(num));
    }

}
