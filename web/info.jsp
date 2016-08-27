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
        <title>info</title>
        <script  type="text/javascript" src="script/onlineNumber.js"></script>
       
        <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    </head>
    <body>
        
        <div class="container">
        <h1>Your Account page:</h1>
        
        <p>this is some infomation about your account:</p>
        
        <p><font size="4">The online number is:</font>
            <button type="button" id ="onlineNumber" class="btn btn-info">...</button>
        </p>
        
        <form action="login" method="post">
            <fieldset disabled>

            <div class="form-group">
                <label for="disabledTextInput">Your Account</label>
                <input type="text" id="disabledTextInput" class="form-control" placeholder=${account}>
            </div>

            <div class="form-group">
                <label for="disabledTextInput">Your Username</label>
                <input type="text" id="disabledTextInput" class="form-control" placeholder=${username}>
            </div>

            <div class="form-group">
                <label for="disabledTextInput">Your Login Time</label>
                <input type="text" id="disabledTextInput" class="form-control" placeholder=${message}>
            </div>
            
            <p class="bg-warning"><font size="4">${warn}</font></p>
            
            </fieldset>
        </form>

        <form action="login" method="post">
            <input type="hidden" name="action" value="logout">
            <input type="submit" value ="logout" class="btn btn-danger">
        </form>
        <br>
        
        <p><font size="4">To change your password: </font></p>

        <form action="pwd" method="get">
            <input type="submit" value ="Edit password" class="btn btn-warning">
        </form>
        </div>
        
    </body>
</html>
