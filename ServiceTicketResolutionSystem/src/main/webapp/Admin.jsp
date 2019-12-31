<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String registrationStatus = (String) request.getAttribute("registrationStatus");
	String message = (String) request.getAttribute("message");
	if (message != null && !message.equals("Admin"))
		out.println(message);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminHome</title>
</head>
<body>

	<h2>AdminHome</h2>

	<%
		if (registrationStatus != null) {
			out.println(registrationStatus);
		}
	%>
	<ul>
		<li><a href="userRegistration">Add User</a></li>
		<li><a href="engineerDepartments">Add
				ServiceEngineer</a></li>
		<li><a href="userRoles">View User</a></li>
		<li><a href="departments">Add
				Department</a></li>

	</ul>
	<a href='logout'><button>LogOut</button></a>

</body>
</html>

<%
	request.removeAttribute("message");
	request.removeAttribute("registrationStatus");
%>