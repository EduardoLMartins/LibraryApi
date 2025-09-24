package io.github.eduardolemos.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Test
	void atualizarAutorDoLivro() {
		UUID id = (UUID.fromString("045ffe33-79b6-4912-846c-314f657c4e1c"));
		var livroParaAtualizar = livroRepository.findById(id).orElse(null);
		
		UUID idAutor = UUID.fromString("ccba2eaf-fb7b-46bc-a0d2-8a93f8b505fa");
		var autor = autorRepository.findById(idAutor).orElse(null);
		
		livroParaAtualizar.setAutor(autor);
		livroRepository.save(livroParaAtualizar);
	}
	
	@Test
	void deletarPorIdSemcascade() {
		
		UUID id = (UUID.fromString("045ffe33-79b6-4912-846c-314f657c4e1c"));
	
		livroRepository.deleteById(id);
		
	}
	
	@Test
	void deletar() {
		
		UUID id = (UUID.fromString("045ffe33-79b6-4912-846c-314f657c4e1c"));
		var livroParaAtualizar = livroRepository.findById(id).orElse(null);
	
		livroRepository.deleteById(id);
		
	}
	
	@Test
	//@Transactional garante que todas as operações do método
	//(ou classe) rodem em uma transação única, fazendo commit se tudo der certo ou rollback se der erro.
	@Transactional
	void buscarLivroTest() {
		UUID id = UUID.fromString("11e73b06-1250-4cbf-ac73-99122c766ed0");
		var livro = livroRepository.findById(id).orElse(null);
		System.out.println("Livro:");
		System.out.println(livro.getTitulo());
		System.out.println("Autor:");
		System.out.println(livro.getAutor().getNome());
	}

	@Test
	void listarLivrosTituloTest() {
		List<Livro> livros = livroRepository.findByTitulo("As cronicas de ninguem com autor novo");
		livros.forEach(System.out:: println);
		
	}
	
	@Test
	void listarLivrosIsbnnTest() {
		List<Livro> livros = livroRepository.findByIsbn("20123-1231");
		livros.forEach(System.out:: println);
		
	}
	
	@Test
	void listarLivrosTituloAndPreco() {
		List<Livro> livros = livroRepository.findByTituloAndPreco("Aprender é bom", new BigDecimal(100.00));
		livros.forEach(System.out::println);
	}
	
	@Test
	void listarLivrosBetweendataPublicacao() {
		
		List<Livro> livros = livroRepository.findByDataPublicacaoBetween(LocalDate.of(2000, 1, 01), LocalDate.of(2010, 1, 01));
		livros.forEach(System.out::println);
	}
	
	@Test
	void listarLivrosPorPalavra() {
		List<Livro> livros = livroRepository.findByTituloLike("Aprender%");
		livros.forEach(System.out::println);
	}
	
}
