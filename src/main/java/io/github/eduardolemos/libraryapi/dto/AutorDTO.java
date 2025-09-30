package io.github.eduardolemos.libraryapi.dto;

import java.time.LocalDate;

import io.github.eduardolemos.libraryapi.model.Autor;

public record AutorDTO(
		String nome, 
		LocalDate dataNascimento, 
		String nacionalidade) {



public Autor mapearParaAutor() {
	Autor autor = new Autor();
	autor.setNacionalidade(this.nacionalidade);
	autor.setNome(this.nome);
	autor.setDataNascimento(this.dataNascimento);
	return autor;
}}
