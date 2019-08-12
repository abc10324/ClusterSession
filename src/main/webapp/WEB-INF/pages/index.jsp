<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
	<h2>Index</h2>
	
	<a href="<c:url value="/SessionTest"/>">
		<button>To Session Test Page</button>
	</a>
	<br/>
	<br/>
	<a href="<c:url value="/Regist"/>">
		<button>To User Regist Page</button>
	</a>
	<br/>
	<br/>
	<a href="<c:url value="/Login"/>">
		<button>To User Login Page</button>
	</a>
	<br/>
	<br/>
	<a href="<c:url value="#"/>">
		<button>To WebSocket Test Page</button>
	</a>
</body>
</html>