<%@page import="bean.Departments"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	@SuppressWarnings("unchecked")
	List<Departments> departments = (List<Departments>) request.getAttribute("departments");
	String message = (String) request.getAttribute("message");

	if (message != null) {
		out.println(message);
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminAlterationPage</title>
</head>
<body>
	<table>
		<tr>
			<th>DepartmentID</th>
			<th>DepartmentName</th>
		</tr>
		<%
			if (departments != null) {
				for (int i = 0; i < departments.size(); i++) {
					if (departments.get(i).getDepartmentID() != 100) {
		%><tr>
			<td><%=departments.get(i).getDepartmentID()%></td>
			<td><%=departments.get(i).getDepartmentName()%></td>
		</tr>
		<%
			}
				}
			}
		%>
	</table>

	<h1>ADD DEPARTMENT</h1>

	<form action="addDepartment">

		Enter DepartmentID: <input type="number" name="departmentID">
		Enter DepartmentName: <input type="text" name="departmentName">

		<input type="submit" name="submit" value="Add Department">

	</form>

</body>
</html>

<%
	request.removeAttribute("message");
	request.removeAttribute("departments");
%>