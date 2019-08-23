<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 

String registrationStatus = (String) request.getAttribute("registrationStatus"); 
String message = (String) request.getAttribute("message");
if(message!=null)
	out.println(message);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminHome</title>
</head>
<body>
Welcome Admin!!!

<% 

if(registrationStatus!=null)
{
	out.println(registrationStatus);
}

%>
<ul>
		<li><a href="Register.jsp">Add User</a></li>
		<li><a href="AdminServlet?option=getDepartments">Add ServiceEngineer</a></li>
		<li><a href="AdminServlet?option=getRoles">View User</a></li>
</ul>
			<a href='AdminServlet?value=Logout'><button>LogOut</button></a>

</body>
</html>

<%

request.removeAttribute("message");
request.removeAttribute("registrationStatus");

%>