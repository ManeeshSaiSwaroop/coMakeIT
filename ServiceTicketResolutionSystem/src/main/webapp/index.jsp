
<%

String message = (String)request.getAttribute("message"); 
if(message!=null)
	out.println(message);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
input {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
}

input[type=submit] {
	background-color: #4CAF50;
	color: white;
}
</style>
</head>
<body>

	<div class="container">
		<form action="login" method="post">
			Username: <input type="text" name="username" id="username" required><br>
			<% 

if(message!=null && message.equals("UserDoesNotExist"))
{
	out.println("Username does not exist");
}

%>
			<br> <br>Password: <input type="password" name="password"
				id="password" required><br>
			<% 

if(message!=null && message.equals("WrongPassword"))
{
	out.println("You have entered wrong password");
}

%>
			<br> <input type="submit" value="Login" name="submit"
				style="align-self: center;">&nbsp;&nbsp; You don't have an
			account?<a href="Register.jsp">Click here for registration</a>
		</form>
	</div>

</body>
</html>
<%request.removeAttribute("message"); %>
