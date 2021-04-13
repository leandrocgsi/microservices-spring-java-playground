package br.com.erudio.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.erudio.model.Book;
import br.com.erudio.model.response.Cambio;
import br.com.erudio.repository.BookRepository;

@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;

	@GetMapping(value = "/{id}/{currency}", produces = { "application/json" })
	public Book findById(@PathVariable("id") Long id,
			@PathVariable("currency") String currency) {

		String port = environment.getProperty("local.server.port");
		
		Book book = repository.getById(id);
		
		HashMap<String, String> params = new HashMap<>();
		
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
			
		var response = new RestTemplate()
			.getForEntity("http://localhost:8000/cambio-service/"
					+ " {amount}/{from}/{to}",
					Cambio.class, params);
		
		Cambio cambio = response.getBody();
		book.setEnvironment(port);
		book.setPrice(cambio.getConvertedValue());
		book.setCurrency(currency);
		return book;
	}
}