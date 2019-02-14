<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% if (Language.bundle.getString("Language").equals("ar")) {
	out.println("<html dir='rtl'>");
} %>

<head>
	<link rel="stylesheet" type="text/css" href="CSS/registration.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ page import="com.example.common.*" %>
	<title>User list</title>
</head>

<body>
	<h1><%= Language.bundle.getString("User_title") %></h1>
	<%@ page import="java.util.*,com.example.bo.*,java.io.*" %>
	<%  @SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>)request.getAttribute("users"); 
		Iterator<User> itr = users.iterator();
		User temp;
		out.print("<table bordercolor=#343>");
		//Table headers
		out.print("<tr><td>"+ Language.bundle.getString("Name") +"</td>" +
					"<td>"+ Language.bundle.getString("Age") +"</td>" +
					"<td>"+ Language.bundle.getString("Level") +"</td>" +
					"<td>"+ Language.bundle.getString("Email") +"</td><td></td><td></td>");
		while(itr.hasNext()) {
			temp = itr.next();
			out.print("<tr><td>" + temp.getName() + "</td>");
			out.print("<td>" + temp.getAge() + "</td>");
			out.print("<td>" + temp.getLevel() + "</td>");
			out.print("<td>" + temp.getEmail() + "</td>"); %>
		<!-- User update -->
		<%= "<td>" %><form action="update_user.jsp" method="POST">
			<input type="hidden" name="id" value="<%= temp.getId() %>" />
   			<input type="hidden" name="name" value="<%= temp.getName() %>" />
   			<input type="hidden" name="age" value="<%= temp.getAge() %>" />
   			<input type="hidden" name="level" value="<%= temp.getLevel() %>" />
   			<input type="hidden" name="email" value="<%= temp.getEmail() %>" />
   			<input type="submit" class="update_user_button" value="<%= Language.bundle.getString("Update_user") %>">
   		</form><%= "</td>" %>
		<!-- User delete -->
		<%= "<td>" %><form action="DeleteUserServlet" method="POST">
			<input type="hidden" name="toDelete" value="<%= temp.getId() %>" />
			<input type="submit" class="update_user_button" value="<%= Language.bundle.getString("Delete_user") %>">
		</form><%= "</td>" %>
	<% } 
	out.print("</table>"); %>
	<br>
	<a title="add user" href="register.jsp"><%= Language.bundle.getString("Add_user") %></a><br>
</body>
</html>