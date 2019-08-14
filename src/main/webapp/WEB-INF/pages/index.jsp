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
	<c:choose>
		<c:when test="${empty userInfo}">
			<h2>Index</h2>
		</c:when>
		<c:otherwise>
			<h2>Hello ${userInfo.name}</h2>		
		</c:otherwise>
	</c:choose>
	
	
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
	<c:choose>
		<c:when test="${empty userInfo}">
			<a href="<c:url value="/Login"/>">
				<button>Login</button>
			</a>
			<br/>
			<br/>
		</c:when>
		<c:otherwise>
			<a href="<c:url value="/Logout"/>">
				<button>Logout</button>
			</a>
			<br/>
			<br/>	
			<a href="<c:url value="/WebSocketTest"/>">
				<button>To WebSocket Test Page</button>
			</a>
		</c:otherwise>
	</c:choose>
	
</body>
</html>