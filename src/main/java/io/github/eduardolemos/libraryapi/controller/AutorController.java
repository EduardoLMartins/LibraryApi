package io.github.eduardolemos.libraryapi.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

}
