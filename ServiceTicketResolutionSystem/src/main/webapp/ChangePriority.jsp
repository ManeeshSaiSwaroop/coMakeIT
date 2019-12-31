<%@page import="bean.Priorities"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	@SuppressWarnings("unchecked")
	List<Priorities> priorities = (List<Priorities>) request.getAttribute("priorities");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PriorityChangeOfGivenTicket</title>
</head>
<body>
	<form action="updateTicketPriority" method="post">
		Enter the TicketID to change it's priority:<input type="text"
			name="ticketID"> Select TicketPriority:<select
			name="priorityID">
			<%
				if (priorities != null) {
					for (int i = 0; i < priorities.size(); i++) {
						if(priorities.get(i).getPriorityID()!=0) {
			%><option value="<%=priorities.get(i).getPriorityID()%>"><%=priorities.get(i).getPriorityName()%></option>
			<%
				}
				}
				}
			%>
		</select> <br> <input type="submit" name="option" value="UpdatePriority">
	</form>
</body>
</html>
<% request.removeAttribute("priorities"); %>
