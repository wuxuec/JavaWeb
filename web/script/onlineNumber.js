/**
 * 更新查询在线人数的js脚本
 *
 * @version 	1.0
 * @author 	武家辉
 */


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
    if (xmlHttp.readyState === 4) {
        if (xmlHttp.status === 200) {
            updateNumber();
        }
    }
}
            
function updateNumber() {
    var number = xmlHttp.responseText;
    var userMessageElement = document
        .getElementById("onlineNumber");
    userMessageElement.innerHTML =   number;
}
            
window.setInterval(updateOnlineNumber, 2000);