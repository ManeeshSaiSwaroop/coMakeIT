<%@page import="BeanClasses.TicketDetails"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
	String option = (String) request.getAttribute("option");
	%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">

table {border-collapse: collapse;}
td, th {border: 3px solid}

</style>

<title>Insert title here</title>
</head>
<body>
<%if(option.equals("viewTickets")){ 
	@SuppressWarnings("unchecked")
	List<TicketDetails> tickets = (List<TicketDetails>) request.getAttribute("Tickets");

%>
<table>

<tr><th>TicketID</th><th>DateOfIssue</th><th>RequestedEndDate</th><th>ProblemDescription</th><th>TicketStatus</th><th>TicketPriority</th><th>Customer Username</th><th>StartTime</th><th>CompletionTime</th></tr>
<% 

for(int i=0; i<tickets.size(); i++)
{
	
%>
<tr><td><%=tickets.get(i).getTicketID() %></td><td><%= tickets.get(i).getDateOfIssue() %></td><td><%if(tickets.get(i).getRequestedEndDate()!=null){ out.print(tickets.get(i).getRequestedEndDate()); } else{ out.print("-----"); } %></td><td><%= tickets.get(i).getProblemDescription() %></td><td><%= tickets.get(i).getTicketStatus() %></td><td><%= tickets.get(i).getPriorities().getPriorityName() %></td><td><%= tickets.get(i).getCredentials().getUsername() %></td><td><%if(tickets.get(i).getStartTime()!=null){ out.print(tickets.get(i).getStartTime()); } else{ out.print("-----"); } %></td><td><%if(tickets.get(i).getCompletionTime()!=null){ out.print(tickets.get(i).getCompletionTime()); } else{ out.print("-----"); } %></td></tr>
<%	
}
%>

</table>

<br><br><br>

<a href='ServiceEngineerServlet?option=resolveTickets'><button>ResolveTickets</button></a>
<a href='ServiceEngineerServlet?option=changePriority'><button>ChangePriority</button></a>

<% } else if(option.equals("resolveTickets")){ 

	TicketDetails ticketDetails = (TicketDetails)request.getAttribute("TicketDetails");
	
%>

<table>

<tr><th>TicketID</th><th>DateOfIssue</th><th>RequestedEndDate</th><th>ProblemDescription</th><th>TicketStatus</th><th>TicketPriority</th><th>Customer Username</th><th>StartTime</th><th>CompletionTime</th></tr>

<% 

if(!"0".equals(ticketDetails.getTicketID()))
{

%>

<tr><td><%=ticketDetails.getTicketID() %></td><td><%= ticketDetails.getDateOfIssue() %></td><td><%if(ticketDetails.getRequestedEndDate()!=null){ out.print(ticketDetails.getRequestedEndDate()); } else{ out.print("-----"); } %></td><td><%= ticketDetails.getProblemDescription() %></td><td><%= ticketDetails.getTicketStatus() %></td><td><%= ticketDetails.getPriorities().getPriorityName() %></td><td><%= ticketDetails.getCredentials().getUsername() %></td><td><%if(ticketDetails.getStartTime()!=null){ out.print(ticketDetails.getStartTime()); } else{ out.print("-----"); } %></td><td><%if(ticketDetails.getCompletionTime()!=null){ out.print(ticketDetails.getCompletionTime()); } else{ out.print("-----"); } %></td></tr>

<%

}

%>

</table>

<br><br><br>
<a href='ServiceEngineerServlet?option=closeTicket'><button>closeTicket</button></a>

<%	
 } %>
</body>
</html>

<%

request.removeAttribute("option");
request.removeAttribute("Tickets");

%>