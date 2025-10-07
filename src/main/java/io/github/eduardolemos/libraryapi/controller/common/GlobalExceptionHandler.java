package io.github.eduardolemos.libraryapi.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.eduardolemos.libraryapi.dto.ErroCampoDTO;
import io.github.eduardolemos.libraryapi.dto.ErroRespostaDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	public ErroRespostaDTO handleMethodArgumentNotvalidException( MethodArgumentNotValidException e) {
		List <FieldError> fieldError = e.getFieldErrors();
		List<ErroCampoDTO> listaErros = fieldError.stream().map(fe -> new ErroCampoDTO(fe.getField(), fe.getDefaultMessage()))
		.collect(Collectors.toList());
		return new  ErroRespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação.", listaErros);
	}

}
