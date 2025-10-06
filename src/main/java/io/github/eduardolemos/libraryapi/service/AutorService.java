package io.github.eduardolemos.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.eduardolemos.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.repository.AutorRepository;
import io.github.eduardolemos.libraryapi.repository.LivroRepository;
import io.github.eduardolemos.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {

	private final AutorRepository autorRepository;
	private final AutorValidator autorValidator;
	private final LivroRepository livroRepository;
	
	public Autor salvar(Autor autor) {
		autorValidator.validar(autor);
		return autorRepository.save(autor);
	}
	
	public void atualizar(Autor autor) {
		if(autor.getId() == null) {
			throw new IllegalArgumentException("Para atualizar é necessario que o autor ja esteja salvo na base");
		}
		autorValidator.validar(autor);
		autorRepository.save(autor);
	}

	public Optional<Autor> obterPorId(UUID uuid) {
		return autorRepository.findById(uuid);
	}

	public void deletar(Autor autor) {
		if(possuiLivro(autor)) {
			throw new OperacaoNaoPermitidaException("Não é permitido excluir o "
					+ "autor que possui livros cadastrados!");
		}
		autorRepository.delete(autor);
	}

	public List<Autor> pesquisa(String nome, String nacionalidade) {
		if (nome != null && nacionalidade != null) {
			return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
		} else if (nome != null) {
			return autorRepository.findByNome(nome);
		} else if (nacionalidade != null) {
			return autorRepository.findByNacionalidade(nacionalidade);
		} else {
			return autorRepository.findAll();
		}

	}
	
	public boolean possuiLivro(Autor autor) {
		
		return livroRepository.existsByAutor(autor);
		
	}

}
