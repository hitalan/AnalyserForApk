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
    This is my analyzer  version is 20160719 <br/>
    fixed the bad connection with the redis and when bad connection happened there will not be some bad exception and quick the speed of the analyzer</br>
    -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------<br/>
    update log:<br/>
    20160712<br/>
    fixed the bug of two package name may cause the bad analyzer and change the second download just download one</br>
    20160708<br/>
    avoid the not relation apk before maybe relation apk do the different package name until the last apk package is not the same <br/>
  </body>
</html>
