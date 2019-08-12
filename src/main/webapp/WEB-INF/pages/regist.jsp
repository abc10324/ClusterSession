<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Regist</title>
	<script
	  src="https://code.jquery.com/jquery-1.12.4.min.js"
	  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
	  crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.serializeJSON/2.9.0/jquery.serializejson.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#ajax-submit").click(function(){
				$("#error-msg").text("");
				$.ajax({
					url: '<c:url value="/Regist"/>',
				   type: "POST",
			contentType: "application/json",
				   data: JSON.stringify($("#regist-form").serializeJSON()),
				success: function(data){
							if(data){
								alert("Regist Success!");
								$("#regist-form input").val("");
							} 				
						},
				 error: function(data){
					 		if(data){
					 			console.log(data.responseJSON.error);
					 			$("#error-msg").text(data.responseJSON.error);
					 		}
				 		}
				});
			});
		});
	</script>
</head>
<body>
	<h2>Regist</h2>
	
	<form id="regist-form">
		<input type="text" name="id" placeholder="input id"/><br/><br/>
		<input type="text" name="name" placeholder="input name"/><br/><br/>
		<select>
			<option value="male" selected="selected">男</option>
			<option value="woman" >女</option>
		</select><br/><br/>		
		<input type="text" name="email" placeholder="input email"/><br/><br/>
		<input type="date" name="birth" /><br/><br/>
		<input type="text" name="phone" placeholder="input phone"/><br/><br/>
	</form>
	
	
	<button id="ajax-submit">Submit</button>
	<span id="error-msg" style="color:red" ></span>
	<br/>
	<hr/>
	<a href="<c:url value="/"/>">
		<button>To Index Page</button>
	</a>
</body>
</html>