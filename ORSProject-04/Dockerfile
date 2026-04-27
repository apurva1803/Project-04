FROM tomcat:9

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Change Tomcat port from 8080 → 1999
RUN sed -i 's/port="8080"/port="1999"/' /usr/local/tomcat/conf/server.xml

# Copy WAR file
COPY target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

# Expose new port
EXPOSE 1999