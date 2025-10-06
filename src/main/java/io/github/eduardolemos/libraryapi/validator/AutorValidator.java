package io.github.eduardolemos.libraryapi.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.eduardolemos.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.repository.AutorRepository;

@Component
public class AutorValidator {

	
	private AutorRepository autorRepository;
	
	public AutorValidator(AutorRepository autorRepository) {
		this.autorRepository = autorRepository;
	}
	
	public void validar(Autor autor) {
		if(existeAutorCadastrado(autor)) {
			throw new RegistroDuplicadoException("Já existe um autor cadastrado!");
		}
		
	}
	
	private boolean existeAutorCadastrado(Autor autor) {
		
		Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(),autor.getDataNascimento(), autor.getNacionalidade());
		
		if(autor.getId() == null) {
			return autorEncontrado.isPresent();
		}
		return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
	
	
	}
}
