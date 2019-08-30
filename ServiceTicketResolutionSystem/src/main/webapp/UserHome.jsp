<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

String message = request.getParameter("message"); 
if(message!=null)
	out.println(message);

%>

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
		<li><a href="raiseTicket">Submit ticket</a></li>
		<li><a href="viewTickets">Open and track support tickets</a></li>
	</ul>
	<a href="logout"><button>LogOut</button></a>
</body>
</html>

<%

request.removeAttribute("message");

%>