package io.github.eduardolemos.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.model.Livro;

public interface LivroRepository  extends JpaRepository<Livro, UUID>{

	//Query method
	//select * from livro where id_autor = id
	List<Livro> findByAutor(Autor autor);
	
	// select * from livro where titulo = titulo
	List<Livro> findByTitulo(String titulo);

	// select * from livro where isbn = isbn
	List<Livro> findByIsbn(String isbn);
	
	// select * from livro where titudo = titulo or isbn = isbn
	List<Livro> findByTituloAndPreco(String titulo, BigDecimal bigDecimal);
	
	// select * from livro where data_publicacao between ? and ?
	List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);
	
	// select * from livro where titulo like ?
	List<Livro> findByTituloLike(String titulo);
}
