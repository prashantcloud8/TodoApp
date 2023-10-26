package com.todo.service;

import com.todo.entity.Todo;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface TodoService {

    Todo saveTodo(Todo todo);

    List<Todo> getAllTodos();

    Todo getTodoById(Long id);

    boolean deleteById(Long id);

    boolean existsById(Long id);
}
