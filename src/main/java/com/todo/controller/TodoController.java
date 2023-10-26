package com.todo.controller;

import com.todo.entity.Todo;
import com.todo.exception.TodoNotFoundException;
import com.todo.model.TodoRequest;
import com.todo.model.TodoResponse;
import com.todo.service.TodoService;
import com.todo.util.TodoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/todos")
public class TodoController {

    private TodoService todoService;

    @Autowired
    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo(@RequestBody Todo todo) {
        todo = todoService.saveTodo(todo);
        return todo != null ? TodoUtil.getTodoResponse(todo) : new TodoResponse();
    }

    @GetMapping
    public List<TodoResponse> getTodos() {
        return todoService.getAllTodos().stream()
                .map(TodoUtil::getTodoResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public TodoResponse getTodoById(@PathVariable("id") Long id) throws TodoNotFoundException {

        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            throw new TodoNotFoundException("Todo is not found");
        }
        return TodoUtil.getTodoResponse(todo);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoResponse updatedTodo(@RequestBody TodoRequest updateTodoRequest, @PathVariable("id") Long id) throws TodoNotFoundException {

        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            throw new TodoNotFoundException("Todo not found");
        }

        Todo updatedTodo = TodoUtil.getUpdatedTodo(updateTodoRequest, todo);

        return TodoUtil.getTodoResponse(todoService.saveTodo(updatedTodo));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) throws TodoNotFoundException {

        if (todoService.existsById(id)) {
            todoService.deleteById(id);
        } else {
            throw new TodoNotFoundException("Todo not found");
        }
    }
}
