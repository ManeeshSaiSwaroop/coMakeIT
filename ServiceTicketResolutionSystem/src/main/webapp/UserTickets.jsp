<%@page import="java.util.List"%>
<%@page import="bean.TicketDetails"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	@SuppressWarnings("unchecked")
	List<TicketDetails> userTickets = (List<TicketDetails>) request.getAttribute("userTickets");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">
table {
	border-collapse: collapse;
}

td, th {
	border: 3px solid
}
</style>

<title>TicketsSubmitted</title>
</head>
<body>
	<table>

		<tr>
			<th>ServiceEngineerID</th>
			<th>TicketID</th>
			<th>DateOfIssue</th>
			<th>RequestedEndDate</th>
			<th>ProblemDescription</th>
			<th>TicketPriority</th>
			<th>TicketStatus</th>
			<th>StartTime</th>
			<th>CompletionTime</th>
		</tr>
		<%
			for (int i = 0; i < userTickets.size(); i++) {
		%>

		<tr>
			<td><%=userTickets.get(i).getDetails().getID()%></td>
			<td><%=userTickets.get(i).getTicketID()%></td>
			<td><%=userTickets.get(i).getDateOfIssue()%></td>
			<td>
				<%
					if (userTickets.get(i).getRequestedEndDate() != null) {
							out.print(userTickets.get(i).getRequestedEndDate());
						} else {
							out.print("-----");
						}
				%>
			</td>
			<td><%=userTickets.get(i).getProblemDescription()%>
			<td><%=userTickets.get(i).getPriorities().getPriorityName()%></td>
			<td><%=userTickets.get(i).getTicketStatus()%></td>
			<td>
				<%
					if (userTickets.get(i).getStartTime() != null) {
							out.print(userTickets.get(i).getStartTime());
						} else {
							out.print("-----");
						}
				%>
			</td>
			<td>
				<%
					if (userTickets.get(i).getCompletionTime() != null) {
							out.print(userTickets.get(i).getCompletionTime());
						} else {
							out.print("-----");
						}
				%>
			</td>
		</tr>

		<%
			}
		%>
	</table>
</body>
</html>

<%
	request.removeAttribute("userTickets");
%>