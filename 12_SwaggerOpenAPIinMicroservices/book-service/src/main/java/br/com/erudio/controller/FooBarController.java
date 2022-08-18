package br.com.erudio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("book-service")
public class FooBarController {
	
	private Logger logger = LoggerFactory.getLogger(FooBarController.class); 
	
	@GetMapping("/v7/foo-bar")
	@Bulkhead(name = "default")
	public String fooBarV7() {
		logger.info("Request to foo-bar is received!");
		return "Foo-Bar!!!";
	}

	@GetMapping("/v6/foo-bar")
	@RateLimiter(name = "default")
	public String fooBarV6() {
		logger.info("Request to foo-bar is received!");
		
		return "Foo-Bar!!!";
	}
	
	@GetMapping("/v5/foo-bar")
	@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
	public String fooBarV5() {
		logger.info("Request to foo-bar is received!");
		
		var response = new RestTemplate()
		  .getForEntity("http://localhost:8080/foo-bar", String.class);
		 
		return response.getBody();
	}

	@GetMapping("/v4/foo-bar")
	@Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
	public String fooBarV4() {
		
		logger.info("Request to Foo-Bar is received!");
		
		var response = new RestTemplate()
				.getForEntity("http://localhost:8080/foo-bar", String.class);
		// return "Foo-bar!!!";
		return response.getBody();
	}

	@GetMapping("/v3/foo-bar")
	@Retry(name = "default")
	public String fooBarV3() {
		
		logger.info("Request to Foo-Bar is received!");
		
		var response = new RestTemplate()
	.getForEntity("http://localhost:8080/foo-bar", String.class);
		// return "Foo-bar!!!";
		return response.getBody();
	}


	@GetMapping("/v2/foo-bar")
	public String fooBarV2() {
		var response = new RestTemplate()
	.getForEntity("http://localhost:8080/foo-bar", String.class);
		// return "Foo-bar!!!";
		return response.getBody();
	}
	
	@GetMapping("/v1/foo-bar")
	public String fooBarV1() {
		new RestTemplate()
	.getForEntity("http://localhost:8080/foo-bar",      String.class);
		return "Foo-bar!!!";
	}

	@GetMapping("/v0/foo-bar")
	public String fooBarV0() {
		return "Foo-Bar!!!";
	}
	
	public String fallbackMethod(Exception ex) {
		return "fallbackMethod foo-bar!!!";
	}
}
