<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Session Test</title>
</head>
<body>
	<h2>Session Test</h2>
	<form action="/ClusterSession/Attr" method="post">
		<input type="text" name="name" placeholder="input name"/><br/>
		<input type="submit" value="submit"/>
	</form>
	
	<br/>
	<hr/>
	<a href="/ClusterSession/Attr">
		<button>Get Session Attribute</button>
	</a>
</body>
</html>