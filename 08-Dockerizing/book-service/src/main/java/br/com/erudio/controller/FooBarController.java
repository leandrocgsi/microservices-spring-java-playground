package br.com.erudio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Foo-bar Endpoint")
@RestController
@RequestMapping("book-service")
public class FooBarController {

	private Logger logger = LoggerFactory.getLogger(FooBarController.class);
	
	@Operation(summary = "Foo-bar operation" )
	@GetMapping("/foo-bar")
	//@Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
	//@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
	//@RateLimiter(name = "default")
	@Bulkhead(name = "foo-bar")
	public String fooBar() {
		logger.info("Request to Foo-Bar is received!");
		/*
		 * var response = new RestTemplate()
		 * .getForEntity("http://localhost:8080/foo-bar", String.class);
		 */
		return "Foo-bar!!!";
		//return response.getBody();
	}
	
	public String fallbackMethod(Exception ex) {
		return "fallbackMethod foo-bar!!!";
	}
}