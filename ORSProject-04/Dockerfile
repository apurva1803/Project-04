FROM tomcat:9

RUN sed -i 's/port="8080"/port="9090"/g' /usr/local/tomcat/conf/server.xml

# Copy WAR file
COPY target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

# Expose new port
EXPOSE 9090
