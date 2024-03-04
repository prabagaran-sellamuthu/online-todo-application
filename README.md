Online Todo Spring Boot Application
# Prerequisites:
JDK-8 and Tomcat-8 installed on your machine
# How to start
git clone https://github.com/prabagaran-sellamuthu/online-todo-application.git
cd online-todo-application
mvn clean install
To test using spring-boot embedded tomcat 
mvn spring-boot:run
Launch this Url in browser
http://localhost:8080/login
To test using external tomcat 8
Place the war file in tomcat webapps folder
Launch this Url in browser
http://localhost:8080/online-todo-application/login

Embedded tomcat
Swagger UI
http://localhost:8080/swagger-ui.html
DB UI
http://localhost:8080/h2-console
Username : sa
Password: password

External Tomcat 
Swagger UI
http://localhost:8080/online-todo-application/swagger-ui.html
DB UI
http://localhost:8080/online-todo-application/h2-console
Username : sa
Password: password
