package io.github.eduardolemos.libraryapi.repository;

import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.eduardolemos.libraryapi.model.Autor;

@SpringBootTest
public class AutorRepositoryTest {

	@Autowired
	AutorRepository autorRepository;
	
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
		
		var id = UUID.fromString("0aeca75b-38c9-4ac6-aca7-2723c3ea3599");
		var autorPraExcluir = autorRepository.findById(id).get();
		
		autorRepository.delete(autorPraExcluir);
		
	}
}
