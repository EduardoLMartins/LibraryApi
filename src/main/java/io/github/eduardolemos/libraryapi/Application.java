package io.github.eduardolemos.libraryapi;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.repository.AutorRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Application.class, args);
		
		AutorRepository autorRepository = context.getBean(AutorRepository.class);
	
		exemploSalvarRegistro(autorRepository);
		
	}

	public static void exemploSalvarRegistro(AutorRepository autorRepository) {
		Autor autor = new Autor();
		autor.setNome("Eduardo");
		autor.setNacionalidade("Braileira");
		autor.setDataNascimento(LocalDate.of(2001, 02, 07));
		
		var autorSalvo = autorRepository.save(autor);
		
		System.out.println("Autor Salvo:" + autorSalvo);
	}
}
