FROM tomcat:9-jdk17

# Clean default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR from correct path (IMPORTANT FIX)
COPY ORSProject-04/target/ORSProject-04.war /usr/local/tomcat/webapps/ROOT.war

# Expose default Tomcat port
EXPOSE 8080

CMD ["catalina.sh", "run"]
