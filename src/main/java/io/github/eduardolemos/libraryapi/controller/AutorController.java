package io.github.eduardolemos.libraryapi.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.eduardolemos.libraryapi.dto.AutorDTO;
import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.service.AutorService;
import lombok.experimental.var;

@RestController
@RequestMapping("/autores")
// http://localhost:8080/autores
public class AutorController {
	 
	private final AutorService autorService;
	
	public AutorController(AutorService autorService) {
		this.autorService = autorService;
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
		var autorEntidade = autor.mapearParaAutor();
		autorService.salvar(autorEntidade);
		
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(autorEntidade.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
		var idAutor = UUID.fromString(id);
		Optional<Autor> autorOptional =  autorService.obterPorId(idAutor);
		if (autorOptional.isPresent()) {
			Autor autor = autorOptional.get();
			AutorDTO dto = new AutorDTO(autor.getId(),autor.getNome(),autor.getDataNascimento()
					,autor.getNacionalidade());
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
		
	}
	
	//indepotente
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") String id){
		var idAutor = UUID.fromString(id);
		Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
		
		if(autorOptional.isEmpty()) { 
			return ResponseEntity.notFound().build();
		}
		autorService.deletar(autorOptional.get());
		return ResponseEntity.noContent().build();
		
	}
	
	
}
