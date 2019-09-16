<%@page import="bean.TicketDetails"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String option = (String) request.getAttribute("option");
	String empty = (String) request.getAttribute("Empty?");
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

<title>ServiceEngineerOutput</title>
</head>
<body>
	<%
		if (option.equals("viewServiceEngineerTickets")) {
			@SuppressWarnings("unchecked")
			List<TicketDetails> serviceEngineerTickets = (List<TicketDetails>) request
					.getAttribute("serviceEngineerTickets");
	%>
	<table>

		<tr>
			<th>TicketID</th>
			<th>DateOfIssue</th>
			<th>RequestedEndDate</th>
			<th>ProblemDescription</th>
			<th>TicketStatus</th>
			<th>TicketPriority</th>
			<th>Customer Username</th>
			<th>StartTime</th>
			<th>CompletionTime</th>
		</tr>
		<%
			for (int i = 0; i < serviceEngineerTickets.size(); i++) {
		%>
		<tr>
			<td><%=serviceEngineerTickets.get(i).getTicketID()%></td>
			<td><%=serviceEngineerTickets.get(i).getDateOfIssue()%></td>
			<td>
				<%
					if (serviceEngineerTickets.get(i).getRequestedEndDate() != null) {
								out.print(serviceEngineerTickets.get(i).getRequestedEndDate());
							} else {
								out.print("-----");
							}
				%>
			</td>
			<td><%=serviceEngineerTickets.get(i).getProblemDescription()%></td>
			<td><%=serviceEngineerTickets.get(i).getTicketStatus()%></td>
			<td><%=serviceEngineerTickets.get(i).getPriorities().getPriorityName()%></td>
			<td><%=serviceEngineerTickets.get(i).getCredentials().getUsername()%></td>
			<td>
				<%
					if (serviceEngineerTickets.get(i).getStartTime() != null) {
								out.print(serviceEngineerTickets.get(i).getStartTime());
							} else {
								out.print("-----");
							}
				%>
			</td>
			<td>
				<%
					if (serviceEngineerTickets.get(i).getCompletionTime() != null) {
								out.print(serviceEngineerTickets.get(i).getCompletionTime());
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

	<br>
	<br>
	<br>

	<a href='resolveTicket'><button>ResolveTickets</button></a>
	<a href='changePriority'><button>ChangePriority</button></a>

	<%
		} else if (option.equals("resolveTickets")) {

			TicketDetails ticketDetails = (TicketDetails) request.getAttribute("TicketDetails");
	%>

	<table>

		<tr>
			<th>TicketID</th>
			<th>DateOfIssue</th>
			<th>RequestedEndDate</th>
			<th>ProblemDescription</th>
			<th>TicketStatus</th>
			<th>TicketPriority</th>
			<th>Customer Username</th>
			<th>StartTime</th>
			<th>CompletionTime</th>
		</tr>

		<%
			if (empty.equals("1") && !"0".equals(ticketDetails.getTicketID())
						&& !(ticketDetails.getTicketStatus().equals("Closed")) && ticketDetails != null) {
		%>

		<tr>
			<td><%=ticketDetails.getTicketID()%></td>
			<td><%=ticketDetails.getDateOfIssue()%></td>
			<td>
				<%
					if (ticketDetails.getRequestedEndDate() != null) {
								out.print(ticketDetails.getRequestedEndDate());
							} else {
								out.print("-----");
							}
				%>
			</td>
			<td><%=ticketDetails.getProblemDescription()%></td>
			<td><%=ticketDetails.getTicketStatus()%></td>
			<td><%=ticketDetails.getPriorities().getPriorityName()%></td>
			<td><%=ticketDetails.getCredentials().getUsername()%></td>
			<td>
				<%
					if (ticketDetails.getStartTime() != null) {
								out.print(ticketDetails.getStartTime());
							} else {
								out.print("-----");
							}
				%>
			</td>
			<td>
				<%
					if (ticketDetails.getCompletionTime() != null) {
								out.print(ticketDetails.getCompletionTime());
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

	<br>
	<br>
	<br>
	<a href='closeTicket'><button>closeTicket</button></a>

	<%
		}
	%>
</body>
</html>

<%
	request.removeAttribute("option");
	request.removeAttribute("Empty?");
	request.removeAttribute("serviceEngineerTickets");
	request.removeAttribute("TicketDetails");
%>