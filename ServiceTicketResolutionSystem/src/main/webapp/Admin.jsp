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
		<li><a href="moveToUserRegistrationPage">Add User</a></li>
		<li><a href="getDepartmentsForServiceEngineer">Add
				ServiceEngineer</a></li>
		<li><a href="getRolesForViewingUser">View User</a></li>
		<li><a href="getDepartmentsForAddingDepartments">Add
				Department</a></li>

	</ul>
	<a href='adminLogout'><button>LogOut</button></a>

</body>
</html>

<%
	request.removeAttribute("message");
	request.removeAttribute("registrationStatus");
%>