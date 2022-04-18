# spring-demo

# run the service
./mvnw spring-boot:run
or
./mvnw clean package (build the JAR)
java -jar target/demo-0.0.1-SNAPSHOT.jar (run the JAR)

# test the service
http://localhost:8080/hello
http://localhost:8080/hello?name=John
http://localhost:8080/greeting?name=John
