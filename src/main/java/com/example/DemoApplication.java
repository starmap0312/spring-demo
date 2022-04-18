package com.example;

import com.example.consumingrest.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // marks the class as a controller where every method returns a data object instead of a view, i.e. @Controller + @ResponseBody
@SpringBootApplication // @Configuration: tags the class as a source of bean definitions for the application context + @ComponentScan: tells Spring to look for controllers in the "com/example" package
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args); // use SpringApplication.run() method to launch the application
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	// Build an executable JAR:
	// you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources and run the JAR
	// ex1. use gradle wrapper (so that you do not need to install gradle)
	//     ./gradlew build (build the JAR)
	//     java -jar build/libs/demo-0.0.1-SNAPSHOT.jar (run the JAR)
	// ex2. use maven wrapper (so that you do not need to install maven)
	//     ./mvnw clean package (build the JAR)
	//     java -jar target/demo-0.0.1-SNAPSHOT.jar (run the JAR)
	// ex3. install and use maven
	//     mvn install (build the JAR)
	//     java -jar target/demo-0.0.1-SNAPSHOT.jar (run the JAR)

	// add a simple REST client that consumes a RESTful Web Service
	// ref: https://spring.io/guides/gs/consuming-rest/
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// uncomment the following if we can access https://quoters.apps.pcfone.io/api/random
    // @Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"https://quoters.apps.pcfone.io/api/random", Quote.class);
			log.info(quote.toString());
		};
	}
}
