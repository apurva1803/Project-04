FROM tomcat:9

<<<<<<< HEAD
# Remove default applications
RUN rm -rf /usr/local/tomcat/webapps/*
=======
RUN sed -i 's/port="8080"/port="8888"/g' /usr/local/tomcat/conf/server.xml
>>>>>>> 6ee7393 (done)

# Copy WAR file
COPY target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

<<<<<<< HEAD
# Change Tomcat port from 8080 to 8888
RUN sed -i 's/port="8080"/port="8888"/g' /usr/local/tomcat/conf/server.xml

# Expose Tomcat port
EXPOSE 8888

# Start Tomcat
CMD ["catalina.sh", "run"]
=======
# Expose new port
EXPOSE 8888
>>>>>>> 6ee7393 (done)
