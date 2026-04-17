FROM tomcat:9

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR as ROOT (so app runs on /)
COPY target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

# Expose port
EXPOSE 8080