package com.example;

import com.example.accessingdatajpa.Customer;
import com.example.accessingdatajpa.CustomerRepository;
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

	// demo 1: define a simple API
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

	// demo 2: add a simple REST client that consumes a RESTful Web Service
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

	// demo 3: define an API that access data with JPA (Java Persistence API)
	// ref: https://spring.io/guides/gs/accessing-data-jpa/
	// ref: https://www.baeldung.com/java-in-memory-databases
	// create a simple JPA application
	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findById(1L);
			log.info("Customer found with findById(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Bauer").forEach(bauer -> {
				log.info(bauer.toString());
			});
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			//  log.info(bauer.toString());
			// }
			log.info("");
		};
		// == Customers found with findAll():
		// Customer[id=1, firstName='Jack', lastName='Bauer']
		// Customer[id=2, firstName='Chloe', lastName='O'Brian']
		// Customer[id=3, firstName='Kim', lastName='Bauer']
		// Customer[id=4, firstName='David', lastName='Palmer']
		// Customer[id=5, firstName='Michelle', lastName='Dessler']
		//
		// == Customer found with findById(1L):
		// Customer[id=1, firstName='Jack', lastName='Bauer']
		//
		// == Customer found with findByLastName('Bauer'):
		// Customer[id=1, firstName='Jack', lastName='Bauer']
		// Customer[id=3, firstName='Kim', lastName='Bauer']
	}
}
