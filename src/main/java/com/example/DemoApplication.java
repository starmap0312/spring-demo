package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // marks the class as a controller where every method returns a data object instead of a view, i.e. @Controller + @ResponseBody
@SpringBootApplication // tags the class as a source of bean definitions for the application context & tells Spring to look for controllers in the "com/example" package
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args); // use SpringApplication.run() method to launch the application
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	// Build an executable JAR:
	// you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources and run the JAR
	// ex1. use gradle wrapper
	//     ./gradlew build (build the JAR)
	//     java -jar build/libs/demo-0.0.1-SNAPSHOT.jar (run the JAR)
	// ex2. use mvn wrapper
	//     ./mvnw clean package (build the JAR)
	//     java -jar target/demo-0.0.1-SNAPSHOT.jar (run the JAR)
	// ex3. use mvn
	//     mvn install (build the JAR)
	//     java -jar target/demo-0.0.1-SNAPSHOT.jar (run the JAR)
}
