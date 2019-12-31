<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Registration Form</title>

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

.container {
	background-color: #f1f1f1;
	padding: 20px;
}

#message {
	display: none;
	background: #f1f1f1;
	color: #000;
	position: relative;
	padding: 20px;
	margin-top: 10px;
}

#message p {
	padding: 10px 35px;
	font-size: 18px;
}

/* Add a green text color and a checkmark when the requirements are right */
.valid {
	color: green;
}

.valid:before {
	position: relative;
	left: -35px;
	content: "✔";
}

.invalid {
	color: red;
}

.invalid:before {
	position: relative;
	left: -35px;
	content: "✖";
}
</style>
<script type="text/javascript">
	function checkPassword() {
		password1 = document.forms["form"]["password"].value;
		password2 = document.forms["form"]["confirmpassword"].value;

		// If password not entered 
		if (password1 == '') {
			alert("Please enter Password");
			return false;
		}

		// If confirm password not entered 
		else if (password2 == '') {
			alert("Please enter confirm password");
			return false;
		}
		// If Not same return False.     
		else if (password1 != password2) {
			alert("\nPassword did not match: Please try again...")
			return false;
		}

		// If same return True. 
		else {
			return true;
		}
	}
</script>
</head>
<body>
	<div class="container">
		<h1 align="center">Registration Page</h1>
		<br>
		<form action="registerUser" name="form" method="Post" id="formID"
			onsubmit="return checkPassword()">
			Enter User name:&nbsp;&nbsp;&nbsp;<input type="text" name="username"
				id="username">

			<%
				if (message != null) {
					out.println(message);
				}
			%>

			Enter Password:&nbsp;&nbsp;&nbsp;<input type="password"
				name="password" id="password"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[^\w\s])(?=.*[A-Z]).{8,50}"
				title="Must contain at least one number, one uppercase, one lowercase letter and one special character with least 8 or more characters"
				onchange="validatePassword();">

			<div id="message">
				<h3>Password must contain the following:</h3>
				<p id="letter" class="invalid">
					A <b>lowercase</b> letter
				</p>
				<p id="capital" class="invalid">
					A <b>capital (uppercase)</b> letter
				</p>
				<p id="number" class="invalid">
					A <b>number</b>
				</p>
				<p id="specialChar" class="invalid">
					A <b>special</b> Character
				</p>
				<p id="length" class="invalid">
					Minimum <b>8 characters</b>
				</p>
			</div>


			Confirm Password:&nbsp;&nbsp;&nbsp;<input type="password"
				name="confirmpassword" id="confirmpassword">
			&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="option"
				value="RegisterUser">
		</form>
	</div>

	<script>
		var myInput = document.getElementById("password");
		var letter = document.getElementById("letter");
		var capital = document.getElementById("capital");
		var number = document.getElementById("number");
		var length = document.getElementById("length");

		// When the user clicks on the password field, show the message box
		myInput.onfocus = function() {
			document.getElementById("message").style.display = "block";
		}

		// When the user clicks outside of the password field, hide the message box
		myInput.onblur = function() {
			document.getElementById("message").style.display = "none";
		}

		// When the user starts to type something inside the password field
		myInput.onkeyup = function() {
			// Validate lowercase letters
			var lowerCaseLetters = /[a-z]/g;
			if (myInput.value.match(lowerCaseLetters)) {
				letter.classList.remove("invalid");
				letter.classList.add("valid");
			} else {
				letter.classList.remove("valid");
				letter.classList.add("invalid");
			}

			// Validate capital letters
			var upperCaseLetters = /[A-Z]/g;
			if (myInput.value.match(upperCaseLetters)) {
				capital.classList.remove("invalid");
				capital.classList.add("valid");
			} else {
				capital.classList.remove("valid");
				capital.classList.add("invalid");
			}

			//Validate special characters
			var specialcharacters = /[^\w\s]/g;
			if (myInput.value.match(specialcharacters)) {
				specialChar.classList.remove("invalid");
				specialChar.classList.add("valid");
			} else {
				specialChar.classList.remove("valid");
				specialChar.classList.add("invalid");
			}

			// Validate numbers
			var numbers = /[0-9]/g;
			if (myInput.value.match(numbers)) {
				number.classList.remove("invalid");
				number.classList.add("valid");
			} else {
				number.classList.remove("valid");
				number.classList.add("invalid");
			}

			// Validate length
			if (myInput.value.length >= 8) {
				length.classList.remove("invalid");
				length.classList.add("valid");
			} else {
				length.classList.remove("valid");
				length.classList.add("invalid");
			}

			//Confirm Password
		}
	</script>

</body>
</html>

<%
	request.removeAttribute("registrationStatus");
%>