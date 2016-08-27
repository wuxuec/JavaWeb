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
       
        <script  type="text/javascript" src="script/onlineNumber.js"></script>
        <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    </head>
    <body>
        
        <div class="container">
        <h1>Thanks for registering our website</h1>
        
        <p>You can fill some message to sign up</p>
        
        <p><font size="4">The online number is:</font>
            <button type="button" id ="onlineNumber" class="btn btn-info">...</button>
        </p>
        
        <form action="register" method="post">

            <div class="form-group">
                <label for="exampleInputEmail1">Add an account</label>
                <input type="text" name="account" class="form-control" id="exampleInputEmail1" placeholder="account">
            </div>
            
            <div class="form-group">
                <label for="exampleInputPassword1">Enter a username</label><br>
                <input type="text" name="username" class="form-control" id="exampleInputEmail1" placeholder="username"> 
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Enter a password</label><br>
                <input type="text" name="password" class="form-control" id="exampleInputEmail1" placeholder="password"> 
            </div>
            <p class="bg-warning"><font size="4">${warn}</font></p>
            
            <input type="submit" value ="Sign Up" class="btn btn-danger">
        </form>
        </div>
        
    </body>
</html>
