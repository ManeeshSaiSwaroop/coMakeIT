<%@page import="BeanClasses.Priorities"%>
<%@page import="BeanClasses.Departments"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	@SuppressWarnings("unchecked")
	List<Departments> departments = (List<Departments>) request.getAttribute("departments");
	@SuppressWarnings("unchecked")
	List<Priorities> priorities = (List<Priorities>) request.getAttribute("priorities");
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	function checkImpFields() {
		var x = document.forms["submitForm"]["ProblemDescription"].value;
		document.getElementById("message").innerHTML = "";
		if (x == "" || x == null) {
			document.getElementById("message").innerHTML += "*Enter Problem Description";
			return false;
		}
		return true;
	}
</script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="UserServlet" onsubmit="return checkImpFields()"
		name="submitForm" method="post">
		*Department:<select name="Department">
			<%
				if (departments != null) {
					for (int i = 0; i < departments.size(); i++) {
			%><option value="<%=departments.get(i).getDepartmentID()%>"><%=departments.get(i).getDepartmentName()%></option>
			<%
				}
				}
			%>
		</select> <br> <br> *ProblemDescription:
		<textarea rows="4" cols="50" name="ProblemDescription"></textarea>
		<p id="message"></p>
		<br> <br> *TicketPriority:<select name="TicketPriority">
			<%
				if (priorities != null) {
					for (int i = 0; i < priorities.size(); i++) {
			%><option value="<%=priorities.get(i).getPriorityID()%>"><%=priorities.get(i).getPriorityName()%></option>
			<%
				}
				}
			%>
		</select> <br> <br> RequestedEndDate:<input type="datetime"
			name="RequestedEndDate" min=sysdate() max="2019-12-31 00:00:00" /> <br>
		<br> <input type="submit" name="option" value="submitTicket">
	</form>
	<br>
	<br>* fields should not be left Empty
</body>
</html>
<% request.removeAttribute("departments");
   request.removeAttribute("priorities");
%>