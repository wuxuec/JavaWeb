<%-- 
    Document   : register
    Created on : Aug 22, 2016, 1:56:10 PM
    Author     : WuJiahui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>   
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <form action="register" method="post">
            
            <label>account:</label>
            <input type="text" name="account">
            <label>username:</label>
            <input type="text" name="username"> 
            <label>password:</label>
            <input type="text" name="password"><br>
            <input type="submit" value ="submit">
        </form>
        
        <p>${warn}</p>
        
    </body>
</html>
