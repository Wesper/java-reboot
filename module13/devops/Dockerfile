FROM docker.io/tomcat:10.1.18-jdk17-temurin-jammy

COPY module13-3.2.0.war /usr/local/tomcat/webapps/module13-3.2.0.war

RUN addgroup  springboot && adduser tomcat
RUN usermod -G springboot tomcat
RUN chown -R tomcat /usr/local/tomcat
USER tomcat

EXPOSE 8080