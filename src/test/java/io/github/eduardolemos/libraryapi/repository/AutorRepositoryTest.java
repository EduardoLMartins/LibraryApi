package io.github.eduardolemos.libraryapi.repository;

import static org.mockito.Mockito.lenient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.model.GeneroLivro;
import io.github.eduardolemos.libraryapi.model.Livro;

@SpringBootTest
public class AutorRepositoryTest {

	@Autowired
	AutorRepository autorRepository;
	
	@Autowired
	LivroRepository livroRepository;
	
	@Test
	public void salvarTest() {
		
			Autor autor = new Autor();
			autor.setNome("Maria");
			autor.setNacionalidade("Brasileira");
			autor.setDataNascimento(LocalDate.of(2003, 07, 26));
			
			var autorSalvo = autorRepository.save(autor);
			
			System.out.println("Autor Salvo:" + autorSalvo);
		
	}
	
	@Test
	public void atualizarTest() {
		var id = UUID.fromString("d31852f3-8386-46cb-940d-a2f6e0dd3e69");
		
		Optional<Autor> possivelAutor =  autorRepository.findById(id);
		
		if(possivelAutor.isPresent()) {
			
			Autor autorEncontrado = possivelAutor.get();
			System.out.println("Dados do Autor:" +autorEncontrado);
			autorEncontrado.setDataNascimento(LocalDate.of(200, 02, 10));
			
			autorRepository.save(autorEncontrado);
		}
	}
	
	@Test
	public void listarTest() {
		List<Autor> lista = autorRepository.findAll();
		lista.forEach(System.out::println);
	}
	
	@Test
	public void countTest() {
		
		System.out.println("Contagem dos autores: " + autorRepository.count());
	}
	
	@Test
	public void deletePorIdTest() {
		
		var id = UUID.fromString("d31852f3-8386-46cb-940d-a2f6e0dd3e69");
		
		autorRepository.deleteById(id);
		
	}
	
	@Test
	public void deletePorObjetoTest() {
		
		var id = UUID.fromString("981c2079-6667-4532-ae71-ae1eeddf3a65");
		var autorPraExcluir = autorRepository.findById(id).get();
		
		autorRepository.delete(autorPraExcluir);
		
	}
	
	@Test
	void salvarAutorComLivrosTest() {
		

		Autor autor = new Autor();
		autor.setNome("Lucas");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1920, 10, 29));
		

		Livro livro = new Livro();
		livro.setTitulo("Aprender é bom");
		livro.setPreco(BigDecimal.valueOf(100));
		livro.setIsbn("20123-1231");
		livro.setDataPublicacao(LocalDate.of(1999, 9, 01));
		livro.setGenero(GeneroLivro.AVENTURA);
		livro.setAutor(autor);
		autor.setLivros(new ArrayList<Livro>());
		autor.getLivros().add(livro);
		
		Livro livro2 = new Livro();
		livro2.setTitulo("Aprender é extraordinario");
		livro2.setPreco(BigDecimal.valueOf(120));
		livro2.setIsbn("20123-1231");
		livro2.setAutor(autor);
		livro2.setDataPublicacao(LocalDate.of(2003, 9, 01));
		livro2.setGenero(GeneroLivro.AVENTURA);
		
		autor.getLivros().add(livro2);
		
		autorRepository.save(autor);
		
		livroRepository.saveAll(autor.getLivros());
		
		
	}
}
