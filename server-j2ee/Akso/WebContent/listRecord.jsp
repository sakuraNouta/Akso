<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,bean.Record,dao.RecordDAO" 
    isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>服药记录</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	
 	String userName = (String)request.getSession().getAttribute("username");
	if(null == userName)
		response.sendRedirect("login.html"); 
%>
	<h1 align='center'>服药记录</h1>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td>编号</td>
			<td>用户</td>
			<td>服药时间</td>
			<td>信息</td>
		</tr>
				
		<%
			RecordDAO dao = new RecordDAO();
			List<Record> records = dao.list();
			for(Record record : records){
		%>
		<tr>
			<td><% out.print(record.getId());%></td>
			<td><% out.print(record.getName());%></td>
			<td><% out.print(record.getDate());%></td>
			<td><% out.print(record.getInfo());%></td>
		</tr>
		<% } %>
	</table>
</body>
</html>