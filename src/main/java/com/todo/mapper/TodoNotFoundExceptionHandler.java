package com.todo.mapper;

import com.todo.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TodoNotFoundExceptionHandler {

    @ExceptionHandler(value = TodoNotFoundException.class)
    public ResponseEntity<String> getTodoNotFoundException(TodoNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
