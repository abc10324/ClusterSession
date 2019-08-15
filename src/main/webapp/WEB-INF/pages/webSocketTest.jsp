<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chat WebSocket</title>
        <script
			  src="https://code.jquery.com/jquery-1.12.4.min.js"
			  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
			  crossorigin="anonymous"></script>	
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.0/sockjs.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
        <script type="text/javascript">
            var stompClient = null;
             
             
            function connect() {
                var socket = new SockJS('<c:url value="/chat"/>');
                stompClient = Stomp.over(socket);  
                stompClient.connect({}, function(frame) {
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/messages', function(messageOutput) {
                        showMessageOutput(JSON.parse(messageOutput.body));
                    });
                    stompClient.subscribe('/topic/userList', function(messageOutput) {
                    	updateUserList(JSON.parse(messageOutput.body));
                    });
                    stompClient.subscribe('/user/queue/messages', function(messageOutput) {
                        showMessageOutput(JSON.parse(messageOutput.body));
                    });
                    
                   	stompClient.send("/app/chat", {}, JSON.stringify({'msg':'userLogin'}));
                });
                
                
            }
             
            function disconnect() {
                if(stompClient != null) {
                    stompClient.disconnect();
                }
                console.log("Disconnected");
            }
             
            function sendMessage() {
                var from = document.getElementById('from').value;
                var to = document.getElementById('to').value;
                var msg = document.getElementById('msg').value;
                stompClient.send("/app/chat", {}, 
                  JSON.stringify({'from':from, 'msg':msg, 'to':to}));
            }
             
            function showMessageOutput(messageOutput) {
                var response = document.getElementById('response');
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';
                
                if(messageOutput.to == 'All')
                	p.style.color = 'red';
                
                p.appendChild(document.createTextNode(messageOutput.from + ": " 
                  + messageOutput.msg + " (" + messageOutput.time + ")" 
                  + " - send from server <" + messageOutput.serverName + ">"));
                response.appendChild(p);
            }
            
            function updateUserList(userList){
            	var loginUser = $("#from").val();
            	
            	$("#to").html("");
            	for(let user of userList){
            		if(user == loginUser)
            			continue;
            		
            		$("#to").append("<option>" + user + "</option>");
            	}
            }
        </script>
    </head>
    <body onload="connect()">
        <div>
            <div>
                <input type="hidden" id="from" value="${userInfo.id}"/>
            </div>
			<a href="<c:url value="/"/>">
				<button>To Index Page</button>
			</a>
			<br/>
			<hr/>
            <div id="conversationDiv">
                <span>To : </span>
                <select id="to"></select>
                <br/><br/>
                <input type="text" id="msg" placeholder="Write a message..."/>
                <button id="sendMessage" onclick="sendMessage()">Send</button>
                <p id="response"></p>
            </div>
        </div>
 
    </body>
</html>