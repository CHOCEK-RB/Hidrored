package com.hidrored.shared.infraestructura.excepciones;

import com.hidrored.modulos.storage.dominio.excepciones.StorageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String TIMESTAMP = "timestamp";
  private static final String STATUS = "status";
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";
  private static final String PATH = "path";

  @ExceptionHandler(StorageException.class)
  public ResponseEntity<Object> handleStorageException(StorageException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put(TIMESTAMP, System.currentTimeMillis());
    body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put(ERROR, "Error de Almacenamiento");
    body.put(MESSAGE, ex.getMessage());
    body.put(PATH, request.getDescription(false));
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put(TIMESTAMP, System.currentTimeMillis());
    body.put(STATUS, HttpStatus.CONFLICT.value());
    body.put(ERROR, "Conflicto");
    body.put(MESSAGE, ex.getMessage());
    body.put(PATH, request.getDescription(false));
    return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put(TIMESTAMP, System.currentTimeMillis());
    body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put(ERROR, "Error Interno del Servidor");
    body.put(MESSAGE, "Ocurrió un error inesperado: " + ex.getMessage());
    body.put(PATH, request.getDescription(false));

    ex.printStackTrace();

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
