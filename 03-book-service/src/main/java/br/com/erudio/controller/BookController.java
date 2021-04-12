package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Book;
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
		book.setEnvironment(port);
		/**Book book = new Book();
		book.setId(Long.valueOf(1));
		book.setAuthor("Nigel Poulton");
		book.setTitle("Docker Deep Dive");
		book.setLaunchDate(new Date());
		book.setPrice(Double.valueOf(15.8));
		book.setCurrency("BRL");
		book.setEnvironment(port); */
		return book;
	}
}