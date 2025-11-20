package com.perfumeria.catalogo.infrastructure.entry_points.exception;


import com.perfumeria.catalogo.domain.exception.*;
import com.perfumeria.catalogo.infrastructure.entry_points.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =============================
    // 400 - BAD REQUEST (Validaciones)
    // =============================
    @ExceptionHandler({
            CampoInvalidoException.class,
            IdProductoInvalidoException.class,
            MarcaInvalidaException.class,
            NombreProductoInvalidoException.class,
            PaginacionInvalidaException.class,
            PrecioInvalidoException.class,
            StockInvalidoException.class,
            TipoInvalidoException.class,
            CarritoVacioException.class,
            CantidadInvalidaException.class,
            StockInsuficienteException.class
    })
    public ResponseEntity<ResponseDTO> handleBadRequest(RuntimeException ex) {
        ResponseDTO response = new ResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // =============================
    // 404 - NOT FOUND
    // =============================
    @ExceptionHandler({
            UsuarioNoEncontradoException.class,
            ProductoNoEncontradoException.class,
            CarritoNoEncontradoException.class,
            ProductoNuloException.class
    })
    public ResponseEntity<ResponseDTO> handleNotFound(RuntimeException ex) {
        ResponseDTO response = new ResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // =============================
    // 400 - Bean Validation
    // =============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String mensajes = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(". "));

        ResponseDTO response = new ResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                mensajes
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // =============================
    // 500 - INTERNAL SERVER ERROR
    // =============================
    @ExceptionHandler({
            ProductoPersistenciaException.class,
            CarritoPersistenciaException.class
    })
    public ResponseEntity<ResponseDTO> handlePersistencia(RuntimeException ex) {
        ResponseDTO response = new ResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error de persistencia: " + ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // =============================
    // 500 - Excepción General
    // =============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGeneral(Exception ex) {
        ResponseDTO response = new ResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor: " + ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}