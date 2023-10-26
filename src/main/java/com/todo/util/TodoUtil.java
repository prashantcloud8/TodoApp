package com.todo.util;

import com.todo.entity.Todo;
import com.todo.model.TodoRequest;
import com.todo.model.TodoResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TodoUtil {

    public static TodoResponse getTodoResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.isCompleted(),
                todo.getOrderNumber(),
                todo.getUrl());
    }

    public static Todo getUpdatedTodo(TodoRequest updateRequest, Todo existingTodo) {
        return new Todo(existingTodo.getId(),
                Optional.ofNullable(updateRequest.getTitle()).orElse(existingTodo.getTitle()),
                Optional.ofNullable(updateRequest.getCompleted()).orElseGet(existingTodo::isCompleted),
                Optional.ofNullable(updateRequest.getOrder()).orElseGet(existingTodo::getOrderNumber));


    }
}
