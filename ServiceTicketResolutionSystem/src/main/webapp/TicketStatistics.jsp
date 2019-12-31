<%@page import="bean.ServiceEngineerDetails"%>
<%@page import="bean.Priorities"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<% String option = (String)request.getAttribute("option"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Statistics</title>
</head>
<body>
	<%

if(option.equals("checkAverageSeverity"))
{
	@SuppressWarnings("unchecked")
	List<String> averageSeverityList = (List<String>) request.getAttribute("averageListPerPriority");
	@SuppressWarnings("unchecked")
	List<Priorities> priorities = (List<Priorities>) request.getAttribute("priorities");
	System.out.println(averageSeverityList);
		%>

	<table>

		<%
	for(int i=0, j=0; i< averageSeverityList.size() && j<priorities.size(); i++, j++) {
		if(priorities.get(j).getPriorityID()!=0) {
		%>

		<tr>
			<th colspan="2"><%= priorities.get(j).getPriorityName()%></th>
		</tr>

		<% 
		if(averageSeverityList.get(i) != null) {
			System.out.print("JSP: " + averageSeverityList.get(i));
			String[] arr = averageSeverityList.get(i).split(":");
			%>
		<tr>
			<td>Hours :</td>
			<td><%= arr[0] %></td>
		</tr>
		<tr>
			<td>Minutes :</td>
			<td><%= arr[1] %></td>
		</tr>
		<tr>
			<td>Seconds :</td>
			<td><%= arr[2] %></td>
		</tr>
		<% 
		} else {
			%>

		<tr>
			<td>Hours :</td>
			<td>---</td>
		</tr>
		<tr>
			<td>Minutes :</td>
			<td>---</td>
		</tr>
		<tr>
			<td>Seconds :</td>
			<td>---</td>
		</tr>
		<% 
			
		}
		
		%>


		<% 
	}
		else
		{
			i--;
		}
	}
%>
	</table>
	<%
		
}
else if(option.equals("getReportStatisticsPerServiceEngineer"))
{
	@SuppressWarnings("unchecked")
	List<String> statistics = (List<String>) request.getAttribute("engineerReportStatistics");
	@SuppressWarnings("unchecked")
	List<ServiceEngineerDetails> engineerDetails = (List<ServiceEngineerDetails>)request.getAttribute("engineers");
	
	%>

	<table>

		<%
	for(int i=0; i< statistics.size(); i++) {
		if(!engineerDetails.get(i).getCredentials().getUsername().equals("DeletedServiceEngineer")) {
		%>

		<tr>
			<th colspan="2"><%= engineerDetails.get(i).getCredentials().getUsername()%>
			</th>
		</tr>

		<% 
		if(statistics.get(i) != null) {
			System.out.print("JSP: " + statistics.get(i));
			String[] arr = statistics.get(i).split(":");
			%>
		<tr>
			<td>Hours :</td>
			<td><%= arr[0] %></td>
		</tr>
		<tr>
			<td>Minutes :</td>
			<td><%= arr[1] %></td>
		</tr>
		<tr>
			<td>Seconds :</td>
			<td><%= arr[2] %></td>
		</tr>
		<% 
		} else {
			%>

		<tr>
			<td>Hours :</td>
			<td>---</td>
		</tr>
		<tr>
			<td>Minutes :</td>
			<td>---</td>
		</tr>
		<tr>
			<td>Seconds :</td>
			<td>---</td>
		</tr>
		<% 
			
		}
		
		%>


		<% 
	}
}
%>
	</table>

	<% 
}
else if(option.equals("checkAgingOfOpenTickets")){
	@SuppressWarnings("unchecked")
	List<Object []> ageOfOpenTickets = (List<Object []>) request.getAttribute("AgeOfOpenTickets");
	int i;
	%>
	<table>

		<%
for(i=0; i< ageOfOpenTickets.size(); i++) {
	%>

		<tr>
			<th colspan="2">TicketID-<%= ageOfOpenTickets.get(i)[0]%>
			</th>
		</tr>

		<% 
	if(ageOfOpenTickets.get(i)[1] != null) {
		System.out.print("JSP: " + ageOfOpenTickets.get(i)[1]);
		String[] arr = String.valueOf(ageOfOpenTickets.get(i)[1]).split(":");
		%>
		<tr>
			<td>Hours :</td>
			<td><%= arr[0] %></td>
		</tr>
		<tr>
			<td>Minutes :</td>
			<td><%= arr[1] %></td>
		</tr>
		<tr>
			<td>Seconds :</td>
			<td><%= arr[2] %></td>
		</tr>
		<% 
	} else {
		%>

		<tr>
			<td>Hours :</td>
			<td>---</td>
		</tr>
		<tr>
			<td>Minutes :</td>
			<td>---</td>
		</tr>
		<tr>
			<td>Seconds :</td>
			<td>---</td>
		</tr>
		<% 
		
	}
	
	%>


		<% 
}
%>
	</table>
	<%
	if(i==0)
	{
		out.println("There are no open tickets assigned to you currently");
	}
}

%>
</body>
</html>

<%

request.removeAttribute("option");
request.removeAttribute("averageListPerPriority");
request.removeAttribute("priorities");
request.removeAttribute("engineerReportStatistics");
request.removeAttribute("engineers");
request.removeAttribute("AgeOfOpenTickets");

%>