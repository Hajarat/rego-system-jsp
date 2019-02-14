<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="CSS/registration.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Refresh" content="4;url=register.jsp">
<title>Redirecting...</title>
</head>
<body>
	<%@ page import="com.example.common.*" %>
	<%  if(response.getHeader("redirectReason").equals(ErrorCodes.INVALID_ENTRY)) {
			out.println("<br>Please enter valid information!<br>");
		} else if(response.getHeader("redirectReason").equals(ErrorCodes.NAME_NOT_ACCEPTED)) {
			out.println("<br>Invalid name! please choose something other than Hassan!<br>");
		} else if(response.getHeader("redirectReason").equals(ErrorCodes.SAVE_USER_FAILED)) {
			out.println("<br>Could not store details, please try again!<br>");
		} else if(response.getHeader("redirectReason").equals(ErrorCodes.DATABASE_CONNECTION)) {
			out.println("<br>Could not Establish Database connection!<br>"); 
		} else if(response.getHeader("redirectReason").equals(ErrorCodes.DATABASE_QUERY)) {
			out.println("<br>Could not Execute query correctly!<br>");
		} else if(response.getHeader("redirectReason").equals(ErrorCodes.REWRITE_UNNECESSARY)) {
			out.println("<br>You must change some details to invoke a rewrite!<br>");
		} else if(response.getHeader("redirectReason").equals(ErrorCodes.ENCRYPTION_ERROR)) {
			out.println("<br>Could not safely store password. Encryption error!<br>");
		} else if(response.getHeader("redirectReason").equals(ErrorCodes.EMAIL_ALREADY_EXISTS)) {
			out.println("<br>This email is already registered by another user!<br>");
		} %>
</body>
</html>