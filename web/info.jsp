<%-- 
    Document   : info
    Created on : Aug 22, 2016, 1:40:27 PM
    Author     : WuJiahui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Infomation</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <span>${username}</span><br>
        <span>${password}</span>
        
        <h2>To change the password!</h2>
        
        <form action="pwd" method="get">
            <input type="submit" value ="Edit pwd">
        </form>
        
        
    </body>
</html>
