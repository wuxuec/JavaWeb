<%-- 
    Document   : password
    Created on : Aug 22, 2016, 2:33:05 PM
    Author     : WuJiahui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Password</title>
    </head>
    <body>
        <h1>Change the password!</h1>
        
        
        <form action="pwd" method="post">
            <label>origin::</label>
            <input type="text" name="origin"> 
            <label>new Password</label>
            <input type="text" name="new"><br>
            <input type="submit" value ="sure">
        </form>
        
        <p>${message}</p>
        
        
    </body>
</html>
