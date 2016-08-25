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
        
        
        
        <script  type="text/javascript">
            
            var xmlHttp;
            function updateOnlineNumber() {
                
                if (window.XMLHttpRequest) {
                    xmlHttp = new XMLHttpRequest();
                } else {
                    xmlHttp = new ActiveObject("Microsoft.XMLHTTP");
                }
                
                var url = "online";
                xmlHttp.open("GET",url, true);
                xmlHttp.onreadystatechange = callback;
                xmlHttp.send();
            }
            
            function callback() {
                if (xmlHttp.readyState == 4) {
                    if (xmlHttp.status == 200) {
                        updateNumber();
                    }
                }
            }
            
            function updateNumber() {
                var number = xmlHttp.responseText;
                var userMessageElement = document
                        .getElementById("onlineNumber");
                userMessageElement.innerHTML = "<font color=\"red\">" 
                        + number + " </font>";
                

            }
            
           //window.setInterval(updateOnlineNumber, 1000);
            
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <span>${account}</span><br>
        <span>${username}</span>
        
        <h2>To change the password!</h2>
        
        <form action="pwd" method="get">
            <input type="submit" value ="Edit pwd">
        </form>
        
        
        <form action="login" method="post">
            <input type="hidden" name="action" value="logout">
            <input type="submit" value ="logout">
        </form>
        
        
        <p>Here is some test message</p>
        <p>${message}</p>
        
        <p>${warn}</p>
        
        
         <h1>Update the number like this:</h1>
        
        <div id ="onlineNumber"></div>
 
        
        
    </body>
</html>
