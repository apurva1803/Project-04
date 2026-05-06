FROM tomcat:9-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR (IMPORTANT PATH FIX)
COPY ORSProject-04/target/ORSProject-04.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
