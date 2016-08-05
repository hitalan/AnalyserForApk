<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  
  <body>
    This is my analyzer  version is 20160804
    change the dfs to ip due to the bad host and fixed the bug of clienurl when is null
    -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------<br/>
    update log:<br/>
    ------------------------------------------------------------------------------------------------------------------</br>
    20160727
    due to the big size of the game apk change the  wait time from 20s to 30s and make the size pool from 10 to 100
    20160720 <br/>
    because of the size of apk in game  change the downloader time from 60s to 100s</br>
    20160719 <br/>
    fixed the bad connection with the redis and when bad connection happened there will not be some bad exception and quick the speed of the analyzer</br>
    20160712<br/>
    fixed the bug of two package name may cause the bad analyzer and change the second download just download one</br>
    20160708<br/>
    avoid the not relation apk before maybe relation apk do the different package name until the last apk package is not the same <br/>
  </body>
</html>
