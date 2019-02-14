<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- had to remove {PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"} from
	 doctype tag to get placeholder attribute to work -->
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
	<script type="text/javascript" src="Javascript/registration.js"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ page import="com.example.common.*" %>
	<title>Update user</title>
</head>

<body>
	<h1><%= Language.bundle.getString("Update_user") %></h1>
	<form method="POST" id="registerform">
	Update the desired information
		<input type="hidden" name="id" value="<%= request.getParameter("id") %>" id="id_update">
		<input type="hidden" name="usage" value="doupdate">
		<table>
			<tr>
				<td><%= Language.bundle.getString("Name") %></td>
				<td><input type="text" name="name" value="<%= request.getParameter("name") %>" id="name_update"></td>
			</tr>	
			<tr>
				<td><%= Language.bundle.getString("Age") %></td>
				<td><input type="text" name="age" value="<%= request.getParameter("age") %>" id="age_update"></td>
			</tr>	
			<tr>
				<td><%= Language.bundle.getString("Level") %></td>
				<td><input type="radio" name="level" value="1" checked id="level_update1"> 1 
				   <input type="radio" name="level" value="2" id="level_update2"> 2 
				   <input type="radio" name="level" value="3" id="level_update3"> 3</td>
			</tr>
			<tr>
				<td><%= Language.bundle.getString("Email") %></td>
				<td><input type="text" class="email_box" name="email" value="<%= request.getParameter("email") %>" id="email_update" readonly></td>
			</tr>
			<tr>
				<td><%= Language.bundle.getString("Password") %></td>
				<td><input type="password" name="old_password"  id="old_pass_update" placeholder="<%= Language.bundle.getString("Old_password") %>">
					<input type="password" name="new_password"  id="new_pass_update" placeholder="<%= Language.bundle.getString("New_password") %>"></td>
			</tr>	
			<tr>
				<td><button onCLick="JavaScript:handleUpdate()" id="submitButton"><%= Language.bundle.getString("Submit") %></button></td>
				<td></td>
			</tr>
		</table>
	</form>
	<br><br>
	<a title="user list" href="ViewUsersServlet"><%= Language.bundle.getString("Return_to_list") %></a><br>
</body>
</html>