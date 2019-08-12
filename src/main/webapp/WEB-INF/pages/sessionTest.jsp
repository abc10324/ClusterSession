<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Session Test</title>
</head>
<body>
	<h2>Session Test</h2>
	
	<form action="<c:url value="/Attr"/>" method="post">
		<input type="text" name="name" placeholder="input name"/><br/>
		<input type="submit" value="submit"/>
	</form>
	
	<br/>
	<hr/>
	<a href="<c:url value="/Attr"/>">
		<button>Get Session Attribute</button>
	</a>
	<br/>
	<hr/>
	<a href="<c:url value="/"/>">
		<button>To Index Page</button>
	</a>
</body>
</html>