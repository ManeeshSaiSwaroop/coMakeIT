<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<h1>HOME</h1>
	<h3>Welcome to Service Engineer Portal</h3>
	<%
		String CloseStatus = (String) request.getAttribute("message");
		if (CloseStatus != null) {
			out.println(CloseStatus);
		}
	%>
	<ul>
		<li><a href="ServiceEngineerServlet?option=viewTickets">Open, Track
				and Solve Tickets submitted to you</a></li>
		<li><a href="ServiceEngineerServlet?option=checkAverageSeverity">You
				can check the average Severity of the Company</a></li>
		<li><a href="ServiceEngineerServlet?option=checkReportStatistics">You
				can also check the report statistics of the average time taken per
				engineer</a></li>
		<li><a href="ServiceEngineerServlet?option=checkAging">And also also
				check the aging of open tickets</a></li>
	</ul>
			<a href="ServiceEngineerServlet?option=Logout"><button>Logout</button></a>
</body>
</html>
<% request.removeAttribute("message"); %>