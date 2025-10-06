package io.github.eduardolemos.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.eduardolemos.libraryapi.dto.AutorDTO;
import io.github.eduardolemos.libraryapi.dto.ErroRespostaDTO;
import io.github.eduardolemos.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.eduardolemos.libraryapi.exceptions.RegistroDuplicadoException;
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
	public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor) {
		try {
		var autorEntidade = autor.mapearParaAutor();
		autorService.salvar(autorEntidade);
		
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(autorEntidade.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();
		} catch(RegistroDuplicadoException e) {
			var erroDTO = ErroRespostaDTO.conflito(e.getMessage());
			return ResponseEntity.status(erroDTO.status()).body(erroDTO);
			
		}
		
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
	public ResponseEntity<Object> deletar(@PathVariable("id") String id){
		
		try {
		var idAutor = UUID.fromString(id);
		Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
		
		if(autorOptional.isEmpty()) { 
			return ResponseEntity.notFound().build();
		}
		autorService.deletar(autorOptional.get());
		return ResponseEntity.noContent().build();
		} catch (OperacaoNaoPermitidaException e) {
			var erroResposta = ErroRespostaDTO.respostaPadrao(e.getMessage());
			return ResponseEntity.status(erroResposta.status()).body(erroResposta);
			
		}
	}
	
	@GetMapping
	public ResponseEntity<List<AutorDTO>> pesquisarAutor(
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "nacionalidade", required = false) String nacionalidade){
		List<Autor> resultadoDaPesquisa= autorService.pesquisa(nome, nacionalidade);
		List<AutorDTO> lista = resultadoDaPesquisa.stream()
				.map(autor -> new AutorDTO(
						autor.getId(),
						autor.getNome(),
						autor.getDataNascimento(),
						autor.getNacionalidade())).collect(Collectors.toList());
		
		return ResponseEntity.ok(lista);
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto) {

		try {
		var idAutor = UUID.fromString(id);
		Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
		
		if(autorOptional.isEmpty()) {
			return ResponseEntity.notFound().build();		}
		
		var autor = autorOptional.get();
		autor.setNome(dto.nome());
		autor.setDataNascimento(dto.dataNascimento());
		autor.setNacionalidade(dto.nacionalidade());
	
		autorService.atualizar(autor);
		return ResponseEntity.noContent().build();	
		}catch(RegistroDuplicadoException e) {
			var erroDTO = ErroRespostaDTO.conflito(e.getMessage());
			return ResponseEntity.status(erroDTO.status()).body(erroDTO);

		}
	}
	
	
}
