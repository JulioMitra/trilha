package com.mitratrilha.trilha.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class handleException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity exception404() { return ResponseEntity.notFound().build();}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exception400(MethodArgumentNotValidException ex) {
        var exception = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(exception.stream().map(DadosErroValidacao::new).toList());
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError exception) {this(exception.getField(), exception.getDefaultMessage());
        }
    }
}
