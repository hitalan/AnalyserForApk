<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  
 <c:if test="${!empty info}">
	   <font color="green"><c:out value="${info}" /></font>
	</c:if>  
  <a href="sendTask">分发任务</a>
    This is my sendTask JSP page. <br>
  </body>
</html>
