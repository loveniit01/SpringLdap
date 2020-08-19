FROM java:8
EXPOSE 10222 
ADD ./target/SprinngLdap-0.0.1-SNAPSHOT.jar SprinngLdap-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","SprinngLdap-0.0.1-SNAPSHOT.jar"]
