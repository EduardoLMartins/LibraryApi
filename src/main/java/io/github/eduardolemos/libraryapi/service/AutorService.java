package io.github.eduardolemos.libraryapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.repository.AutorRepository;

@Service
public class AutorService {

	private final AutorRepository autorRepository;

	public AutorService(AutorRepository autorRepository) {
		this.autorRepository = autorRepository;
	}

	public Autor salvar(Autor autor) {
		return autorRepository.save(autor);
	}

	public Optional<Autor> obterPorId(UUID uuid) {
		return autorRepository.findById(uuid);
	}

	public void deletar(Autor autor) {
		autorRepository.delete(autor);
	}
}
