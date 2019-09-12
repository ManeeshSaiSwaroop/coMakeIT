<%@page import="bean.ServiceEngineerDetails"%>
<%@page import="bean.LoginCredentials"%>
<%@page import="bean.Roles"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String option = (String) request.getAttribute("option");
%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">
table {
	border-collapse: collapse;
}

td, th {
	border: 3px solid
}
</style>

<meta charset="UTF-8">
<title>View Users</title>
</head>
<body>
	<%
		if (option.equals("getRoles")) {
			out.println("Select the type of users you want to view:");
			@SuppressWarnings("unchecked")
			List<Roles> roles = (List<Roles>) request.getAttribute("roles");
	%>
	<form action="viewUsers">
		<select name="roleID">
			<%
				for (int i = 0; i < roles.size(); i++) {
					if(roles.get(i).getRoleID()!=3) {
			%><option value="<%=roles.get(i).getRoleID()%>"><%=roles.get(i).getRoleName()%></option>
			<%
				}
				}
			%>
		</select> <input type="submit" name="option" value="viewUsers">

	</form>
	<%
		} else if (option.equals("viewUsers")) {
			@SuppressWarnings("unchecked")
			List<LoginCredentials> users = (List<LoginCredentials>) request.getAttribute("users");
	%>
	<table>
		<tr>
			<th>Username</th>
			<th>Password</th>
			<th>Action</th>
		</tr>

		<%
			for (int i = 0; i < users.size(); i++) {
				if(!users.get(i).getUsername().equals("Deleted")) {
		%>
		<tr>
			<td><%=users.get(i).getUsername()%></td>
			<td><%=users.get(i).getPassword()%></td>
			<td><a href='deleteUser?Delete=<%=users.get(i).getUsername()%>'><button>Delete</button></a></td>
		</tr>

		<%
			}
			}
		%>


	</table>
	<%
		} else if (option.equals("viewServiceEngineers")) {
			@SuppressWarnings("unchecked")
			List<ServiceEngineerDetails> serviceEngineerList = (List<ServiceEngineerDetails>) request
					.getAttribute("users");
	%>
	<table>
		<tr>
			<th>ServiceEngineerID</th>
			<th>Username</th>
			<th>DepartmentName</th>
			<th>TotalTicketsworkedOn</th>
			<th>CurrentHighPriorityTicketID</th>
			<th>CurrentTicketPriority</th>
			<th>Action</th>
		</tr>

		<%
				for (int i = 0; i < serviceEngineerList.size(); i++) {
					if(!serviceEngineerList.get(i).getCredentials().getUsername().equals("DeletedServiceEngineer")) {
			%>
		<tr>
			<td><%=serviceEngineerList.get(i).getID()%></td>
			<td><%=serviceEngineerList.get(i).getCredentials().getUsername()%></td>
			<td><%=serviceEngineerList.get(i).getDepartments().getDepartmentName()%></td>
			<td><%=serviceEngineerList.get(i).getTotalTicketsWorkedOn()%></td>
			<td><%=serviceEngineerList.get(i).getCurrentHighPriorityTicketID()%></td>
			<td><%=serviceEngineerList.get(i).getPriorities().getPriorityName()%></td>
			<td><a
				href='deleteServiceEngineer?Delete=<%=serviceEngineerList.get(i).getID()%>'><button>Delete</button></a></td>
		</tr>

		<% 
	
	}
	}
	
	%>


	</table>
	<%
	
}
%>
</body>
</html>

<%

request.removeAttribute("option");
request.removeAttribute("roles");
request.removeAttribute("users");

%>