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
         
        <script  type="text/javascript" src="script/onlineNumber.js"></script>
        <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    </head>
    <body>
        
        <div class="container">
        <h1>Welcome to the home page</h1>
        
        <p>You can login with your username and password</p>
        
        <p><font size="4">The online number is:</font>
            <button type="button" id ="onlineNumber" class="btn btn-info">...</button>
        </p>
        
        <form action="login" method="post">
            
            <div class="form-group">
                <label for="exampleInputEmail1">Account Name</label>
                <input type="text" name="account" class="form-control" id="exampleInputEmail1" placeholder="account">
            </div>
            
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label><br>
                <input type="text" name="password" class="form-control" id="exampleInputEmail1" placeholder="password"> 
            </div>
            <p class="bg-warning"><font size="4">${warn}</font></p>
            
            <input type="hidden" name="action" value="login">
            <input type="submit" value ="Login" class="btn btn-success">
        </form>
        <br>
        <p><font size="4">Or, sign up here: </font></p>
        
        <form action="register" method="get">
            <input type="submit" value ="Sign up" class="btn btn-primary">
        </form>   
        </div>
        
    </body>
</html>
