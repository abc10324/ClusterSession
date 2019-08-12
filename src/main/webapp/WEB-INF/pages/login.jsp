<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h2>Login</h2>
	
	<form id="login-form" action="<c:url value="/Login"/>" method="post">
		<input type="text" name="id" placeholder="input id"/><br/><br/>
		<input type="submit" value="Submit"/>
		<span id="error-msg" style="color:red" >${errorMsg}</span>
	</form>
	
	<br/>
	<hr/>
	<a href="<c:url value="/"/>">
		<button>To Index Page</button>
	</a>
</body>
</html>