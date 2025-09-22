package io.github.eduardolemos.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.eduardolemos.libraryapi.model.Livro;

public interface LivroRepository  extends JpaRepository<Livro, UUID>{

}
