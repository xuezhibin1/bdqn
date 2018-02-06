<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>程序起始页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
  </head>
  
  <body>
  <%
  	  // 获得商品分类集合
	  Object obj=request.getAttribute("productCategoryVoList");
  
	  if(obj==null){
		  
		// 重定向到HomeServlet对应的方法
	    response.sendRedirect(request.getContextPath()+"/Home?action=index");
	  }
  %>
  
    This is my JSP page. <br>
  </body>
</html>
