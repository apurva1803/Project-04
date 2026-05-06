FROM tomcat:9.0

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ORSProject-04/target/ORSProject-04.war /usr/local/tomcat/webapps/ORSProject-04.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
