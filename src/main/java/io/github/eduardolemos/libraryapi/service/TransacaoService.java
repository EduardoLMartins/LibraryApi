package io.github.eduardolemos.libraryapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.model.GeneroLivro;
import io.github.eduardolemos.libraryapi.model.Livro;
import io.github.eduardolemos.libraryapi.repository.AutorRepository;
import io.github.eduardolemos.libraryapi.repository.LivroRepository;

@Service
public class TransacaoService {

	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LivroRepository livroRepository;

	
	@Transactional
	public void atualizacaoSemAtualizar() {
		
		var livro = livroRepository.findById(UUID.fromString("11e73b06-1250-4cbf-ac73-99122c766ed0")).orElse(null);
		livro.setDataPublicacao(LocalDate.of(2024, 06, 01));
		
//		livroRepository.save(livro);
	}
	
	
	@Transactional
	public void executar() {
		
		Autor autor = new Autor();
		autor.setNome("teste");
		autor.setNacionalidade("Italiano");
		autor.setDataNascimento(LocalDate.of(2003, 07, 26));
		
		autorRepository.save(autor);

	
		Livro livro = new Livro();
		livro.setTitulo("Chama que chama");
		livro.setPreco(BigDecimal.valueOf(23));
		livro.setIsbn("55412");
		livro.setDataPublicacao(LocalDate.of(1980, 03, 10));
		livro.setGenero(GeneroLivro.MISTERIO);
		
		livro.setAutor(autor);
		livroRepository.save(livro);
		
		
		if (autor.getNome().equals("teste")) {
			throw new RuntimeException("Roolback!");
		}
		
		
	}

}
