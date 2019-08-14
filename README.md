# ClusterSession
Spring MVC integrated with Spring Session , use Redis DB as storage , prepared for clustered environment
  
## Environment  
 > Project: Gradle  
 > Framework: Spring MVC + Spring Data Redis + Spring Data JPA + Spring Session + Spring WebSocket  
 > Server: Tomcat v9.0  
 > Database: Redis + MySQL  

## Function  
 > Entry point : \<url\>/ClusterSession/  
 > User system (Regist/Login/Logout)  
 > Chat room (Broadcast & p2p)  
 > Cluster Session test  

## how to use gradleDeploy.bat and gradleDebugDeploy.bat  
 1.set environment variable TOMCAT_HOME to your tomcat install location (ex:D:\apache-tomcat-9.0.22)  
 2.set environment variable JAVA_HOME to your java install location (ex:C:\Program Files\ojdkbuild\java-1.8.0-openjdk)  
 3.run gradleDeploy.bat/gradleDebugDeploy.bat(for remote debug, open port 8000)
 
