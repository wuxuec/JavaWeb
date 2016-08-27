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
        <script  type="text/javascript" src="script/onlineNumber.js"></script>
        
        <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    </head>
    <body>
        
        <div class="container">
        <h1>Change the password</h1>
        
        <p>You can change your password with verify</p>
        
        <p><font size="4">The online number is:</font>
            <button type="button" id ="onlineNumber" class="btn btn-info">...</button>
        </p>
        
        <form action="pwd" method="post">

            <fieldset disabled>

            <div class="form-group">
                <label for="disabledTextInput">Your Login Time</label>
                <input type="text" id="disabledTextInput" class="form-control" placeholder=${message}>
            </div>

            </fieldset>
            
            <div class="form-group">
                <label for="exampleInputEmail1">Old Password</label>
                <input type="text" name="origin" class="form-control" id="exampleInputEmail1" placeholder="old password">
            </div>
            
            <div class="form-group">
                <label for="exampleInputPassword1">New Password</label><br>
                <input type="text" name="new" class="form-control" id="exampleInputEmail1" placeholder="new password"> 
            </div>
            <p class="bg-warning"><font size="4">${warn}</font></p>
            
            
            <input type="submit" value ="sure" class="btn btn-success">
        </form>
        <br>
        <p><font size="4">Or, sign up here: </font></p>
        
        <form action="register" method="get">
            <input type="submit" value ="Sign up" class="btn btn-primary">
        </form>
        </div>
        
    </body>
</html>
