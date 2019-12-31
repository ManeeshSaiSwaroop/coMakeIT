<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

String message = (String)request.getAttribute("message"); 
if(message!=null && !message.equals("User"))
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
		<li><a href="tickets">Open and track support tickets</a></li>
	</ul>
	<a href="logout"><button>LogOut</button></a>
</body>
</html>

<%

request.removeAttribute("message");

%>