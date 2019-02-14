<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% if (Language.bundle.getString("Language").equals("ar")) {
	out.println("<html dir='rtl'>");
} %>
<head>
	<link rel="stylesheet" type="text/css" href="CSS/registration.css">
	<!-- Importing jQuery library -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script><!--integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU=" crossorigin="anonymous" -->
	<link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
	<!-- End of import -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="Javascript/registration.js"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ page import="com.example.common.*" %>
	<title>Registration</title>
</head>
<body>
<div>
	<h1><%= Language.bundle.getString("Register_title") %></h1>
	<br>
	<form method="POST" id="registerform">
		<input type="hidden" name="usage" value="register">
		<%= Language.bundle.getString("Enter_text") %>
		<table border="0">
			<tr><td><%= Language.bundle.getString("Name") %></td>	<td><input type="text" name="name" id="reg_name"></td></tr>
			<tr><td><%= Language.bundle.getString("Age") %></td>	<td><input type="text" name="age" id="reg_age"></td></tr>
			<tr><td><%= Language.bundle.getString("Level") %></td>	<td><input type="radio" name="level" value="1" checked id="reg_level"> <%= Language.bundle.getString("1") %> 
				   <input type="radio" name="level" value="2"> <%= Language.bundle.getString("2") %> 
				   <input type="radio" name="level" value="3"> <%= Language.bundle.getString("3") %></td></tr>
			<tr><td><%= Language.bundle.getString("Email") %></td>	<td><input type="text" class="email_box" name="email" id="reg_email"></td></tr>
			<tr><td><%= Language.bundle.getString("Password") %></td>	<td><input type="password" name="password" id="reg_password"></td></tr>
			<tr><td><button onCLick="JavaScript:handleRegistration()" id="submitButton"><%= Language.bundle.getString("Submit") %></button></td>	<td></td></tr>
		</table>
	</form>
	<br><br>
	<a title="user list" href="ViewUsersServlet"><%= Language.bundle.getString("View_users") %></a><br>
</div>
</body>
</html>