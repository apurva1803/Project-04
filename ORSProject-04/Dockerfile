FROM tomcat:9.0

# Remove default applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file
COPY target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

# Change Tomcat port from 8080 to 8888
RUN sed -i 's/port="8080"/port="8888"/g' /usr/local/tomcat/conf/server.xml

# Expose Tomcat port
EXPOSE 8888

# Start Tomcat
CMD ["catalina.sh", "run"]
