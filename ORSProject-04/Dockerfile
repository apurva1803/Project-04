FROM tomcat:9

# Copy WAR file
COPY target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

# Expose default Tomcat port
EXPOSE 8080

# Start Tomcat 
CMD ["catalina.sh", "run"]