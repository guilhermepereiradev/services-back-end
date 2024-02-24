package com.soulcode.servicos.controller.exception;

import com.soulcode.servicos.service.exceptions.DataIntegrityViolationException;
import com.soulcode.servicos.service.exceptions.EntidadeNaoEncontradaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler{

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<StandardError> entityNotFound(EntidadeNaoEncontradaException e, HttpServletRequest request){
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Registro encontrado");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setTrace("EntityNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request){
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError("Atributo não pode ser duplicado");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setTrace("DataIntegrityViolationException");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
