package ru.flamexander.spring.data.jdbc.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.flamexander.spring.data.jdbc.demo.dtos.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadArgumentException.class)
    public ResponseEntity<ErrorDto> catchBadArgumentException(BadArgumentException e) {
        return new ResponseEntity<>(new ErrorDto("INVALID_ARGUMENT", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
