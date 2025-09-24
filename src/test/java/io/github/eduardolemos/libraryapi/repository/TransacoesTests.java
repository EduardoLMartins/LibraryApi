package io.github.eduardolemos.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import io.github.eduardolemos.libraryapi.service.TransacaoService;


@SpringBootTest
public class TransacoesTests {

	@Autowired
	TransacaoService transacaoService;
	
	@Test
//	@Transactional // Abre uma transação passivel a commit e rollback no banco de dados
	void transacaoSimples() {
		// salvar um livro
		// salvar o autor
		// alugar o livro
		// enviar email pro locatário
		// notificar que o livro saiu da livraria
		
		transacaoService.executar();
		
	}
	
	@Test
	void transacaoEstadoManaged() {
		transacaoService.atualizacaoSemAtualizar();
	}
}
