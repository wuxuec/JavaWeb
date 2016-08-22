<%-- 
    Document   : index
    Created on : Aug 22, 2016, 12:04:27 PM
    Author     : WuJiahui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>    
    </head>
    <body>
        <h1>Welcome to the home page</h1>
        
        <p>You can login with your username and password</p>
        
        <form action="login" method="post">
            <input type="hidden" name="action" value="login">
            <label>username:</label>
            <input type="text" name="username"> 
            <label>password:</label>
            <input type="text" name="password"><br>
            <input type="submit" value ="Login">
        </form>
        
        <h2>Or, sign up here:</h2>
        
        <form action="register" method="get">
            <input type="submit" value ="Sign up">
        </form>
        
        
    </body>
</html>
