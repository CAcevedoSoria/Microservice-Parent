package com.example.microservicioparent.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParentControllerException {
  @ExceptionHandler(value = ProductNotfoundException.class)
  public ResponseEntity<Object> exception(ProductNotfoundException exception) {
    return new ResponseEntity<>("Parent not found", HttpStatus.NOT_FOUND);
  }

}