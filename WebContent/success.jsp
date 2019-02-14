<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% if (Language.bundle.getString("Language").equals("ar")) {
	out.println("<html dir='rtl'>");
} %>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="CSS/registration.css">
	<%@ page import="com.example.common.*" %>
	<title>Success!</title>
</head>

<body>
	<h1><%= Language.bundle.getString("Success_reg") %></h1>
	<% out.println(Language.bundle.getString("Thanks") + request.getParameter("name")); %>
	<br><a title="back to registration" href="http://localhost:8080/Registration/register.jsp"><%= Language.bundle.getString("Return_to_registration") %></a>
</body>
</html>