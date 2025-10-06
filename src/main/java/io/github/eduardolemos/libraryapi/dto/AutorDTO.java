package io.github.eduardolemos.libraryapi.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.github.eduardolemos.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AutorDTO(
		
		UUID id,
		
		@NotBlank(message = "campo obrigatorio")
		String nome,
		
		@NotNull(message = "campo obrigatorio")
		LocalDate dataNascimento, 
		
		@NotBlank(message = "campo obrigatorio")
		String nacionalidade) {



public Autor mapearParaAutor() {
	Autor autor = new Autor();
	autor.setNacionalidade(this.nacionalidade);
	autor.setNome(this.nome);
	autor.setDataNascimento(this.dataNascimento);
	return autor;
}}
