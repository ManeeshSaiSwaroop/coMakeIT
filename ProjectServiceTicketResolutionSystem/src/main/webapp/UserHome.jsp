<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HOME PAGE</title>
</head>
<body>
		<h1>HOME</h1>
	<h3>Welcome to User Support Portal</h3>
	<ul>
		<li><a href="UserServlet?option=getRequiredTables">Submit ticket</a></li>
		<li><a href="UserServlet?option=viewTickets">Open and track support
				tickets</a></li>
	</ul>
			<a href='UserServlet?option=Logout'><button>LogOut</button></a>
</body>
</html>