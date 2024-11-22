package com.bihsu.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bihsu.literalura.principal.Principal;
import com.bihsu.literalura.service.BookService;
import com.bihsu.literalura.service.PersonaService;
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner{

	@Autowired
	private BookService bookService;

	@Autowired
	private PersonaService personaService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(bookService,personaService);
		principal.mostrarMenu();
	}
}
