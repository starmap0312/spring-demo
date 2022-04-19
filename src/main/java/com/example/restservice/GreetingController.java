package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

// build a simple RESTful web service
// ref: https://spring.io/guides/gs/rest-service/

// @Controller: a meta-annotation of @Component
@RestController // marks the class as a controller where every method returns a data object instead of a view, i.e. @Controller + @ResponseBody
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting") // HTTP GET requests to /greeting are mapped to the greeting() method
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) { // binds the query string parameter "name" into the greeting() method parameter "name"
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
        // traditional MVC controller vs. spring RESTful web service controller
        //   the method returns a data object instead of a view
        //   i.e. server-side view rendering to HTML vs. returns a data object written directly to the HTTP response as JSON
        // Spring uses Jackson (MappingJackson2HttpMessageConverter) to automatically convert the data object to JSON
    }
    // HTTP verbs: ex. @GetMapping, @PostMapping, or @RequestMapping(method=GET), @RequestMapping(method=POST)
}
