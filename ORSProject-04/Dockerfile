FROM tomcat:9-jdk17

# Clean default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy with project name (NOT ROOT)
COPY target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

EXPOSE 8888

CMD ["catalina.sh", "run"]
