package io.github.eduardolemos.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.eduardolemos.libraryapi.model.Autor;
import io.github.eduardolemos.libraryapi.model.GeneroLivro;
import io.github.eduardolemos.libraryapi.model.Livro;
import jakarta.transaction.Transactional;

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
	
	// JPQL -> referencia as entidades e as propriedades
	// select l* from livro as l order by l.titulo
	@Query(" select l from Livro as l order by l.titulo")
	List<Livro> listarTodos();
	
	
	@Query(" select a from Livro l join l.autor a")
	List<Autor> listarAutoresDosLivros();
	
	@Query(" select distinct l.titulo from Livro l")
	List<String> listarNomesDiferentesLivros();
	
	
	// String nova que chegou no java 17 para string longas
	@Query("""
			select l.genero from Livro l
			join l.autor a
			where a.nacionalidade = 'Brasileira'
			order by l.genero
			
			""")
	List<String> listarGenerosAutoresBrasileiros();
	
	// Named parameters -> parametros nomeados
	@Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
	List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, 
			@Param("paramOrdenacao") String nomePropriedade);
	
	
	// Positional parameters
	@Query("select l from Livro l where l.genero = ?1 order by ?2")
	List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, 
			String nomePropriedade);

	
	
	// para fazer delete
	
	@Transactional // serve para abrir uma transação dentro do banco
	@Modifying //modificar registro no banco precisa dessa anotaçao
	@Query(" delete from Livro where genero = ?1")
	void deleteByGenero(GeneroLivro genero);
	
	@Transactional
	@Modifying
	@Query( "update Livro set dataPublicacao = ?1 ")
	void updateDataPublicacao(LocalDate novaData);
	
}
