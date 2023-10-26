package com.todo.service;

import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    TodoRepository repository;

    @InjectMocks
    TodoService todoService = new TodoServiceImpl(repository) ;


    @Test
    public void saveTodo(){
        Todo todo = new Todo(null,"test",true,100);

        Todo saveTodo = new Todo(1L,"test",true,100);

        when(repository.save(todo)).thenReturn(saveTodo);

        todo = todoService.saveTodo(todo);
        assertEquals(todo.getId(),saveTodo.getId());
    }

    @Test
    public void getAllTodos(){
        List<Todo> todos = Stream.of(new Todo(1L,"test",true,100),
                                    new Todo(1L,"test",true,100))
                                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(todos);

        List<Todo> todoList = todoService.getAllTodos();

        assertEquals(todoList.size(),2);
    }

    @Test
    public void getTodoById(){
        Todo todo = new Todo(1L,"test",true,100);

        when(repository.getOne(1L)).thenReturn(todo);

        Todo todoById=todoService.getTodoById(1L);

        assertEquals(todoById,todo);
    }


    @Test
    public void existTodoById(){
        Todo todo = new Todo(1L,"test",true,100);
        when(repository.existsById(1L)).thenReturn(true);

        assertEquals(todoService.existsById(1L), true);
    }
}
