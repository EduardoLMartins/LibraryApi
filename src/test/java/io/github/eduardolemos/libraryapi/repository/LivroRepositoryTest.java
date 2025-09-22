package io.github.eduardolemos.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.model.GeneroLivro;
import io.github.eduardolemos.libraryapi.model.Livro;

@SpringBootTest()
class LivroRepositoryTest {


	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	AutorRepository autorRepository;
	
	@Test
	void salvarTest() {
		
		Livro livro = new Livro();
		livro.setTitulo("As cronicas de ninguem com autor novo");
		livro.setPreco(BigDecimal.valueOf(100));
		livro.setIsbn("123");
		livro.setDataPublicacao(LocalDate.of(2009, 03, 10));
		livro.setGenero(GeneroLivro.AVENTURA);
		
		Autor autor = autorRepository.findById(UUID.fromString("0aeca75b-38c9-4ac6-aca7-2723c3ea3599"))
		.orElse(null);
		
		livroRepository.save(livro);
		
		
		
	}
	
	@Test
	void salvarCascadeTest() {
		
		Livro livro = new Livro();
		livro.setTitulo("As cronicas de ninguem com autor novo");
		livro.setPreco(BigDecimal.valueOf(100));
		livro.setIsbn("123");
		livro.setDataPublicacao(LocalDate.of(2009, 03, 10));
		livro.setGenero(GeneroLivro.AVENTURA);
		
		
		Autor autor = new Autor();
		autor.setNome("Joao");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(2003, 07, 26));
		
		livro.setAutor(autor);
		
		livroRepository.save(livro);
		
		
		
	}

}
