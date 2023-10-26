package com.todo.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.entity.Todo;
import com.todo.exception.TodoNotFoundException;
import com.todo.model.TodoRequest;
import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;
import com.todo.util.TodoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TodoController.class)
public class TodoControllerTest {

    private static final String URL ="/api/v1/todos";

    private static final String URL_PATH_VARIABLE ="/api/v1/todos/1";

    private static final String URL_PATH_VARIABLE_2 ="/api/v1/todos/2";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    TodoRepository repository;

    @MockBean
    private TodoService service;

    @Test
    public void getAllTodosTest() throws Exception {

        when(service.getAllTodos()).thenReturn(Stream.of(
                                                       new Todo(1L,"test",true,100))
                                               .collect(Collectors.toList()));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void getTodoByIdTest() throws Exception {

        when(service.getTodoById(anyLong())).thenReturn(
                new Todo(1L,"test",true,100));

        mockMvc.perform(get(URL_PATH_VARIABLE))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotFoundTodo_thenNotFoundCode() throws Exception {
        String exceptionMessage = "Todo is not found";

        mockMvc.perform(get(URL_PATH_VARIABLE, exceptionMessage)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TodoNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(),exceptionMessage));
    }

    @Test
    public void createTodoTest() throws Exception {
        Todo todo = new Todo(null, "test",true,100);

        when(repository.save(todo)).thenReturn(new Todo(1L, "test",true,100));
        when(service.saveTodo(any(Todo.class))).thenReturn(new Todo(1L, "test",true,100));

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateTodoTest() throws Exception {
        TodoRequest updatedTodoRequest = new TodoRequest("test10",true,1000);
        Todo todo = new Todo(1L,"test1",true,100);

        when(service.getTodoById(anyLong())).thenReturn(todo);

        Todo updatedTodo = TodoUtil.getUpdatedTodo(updatedTodoRequest, todo);

        when(service.saveTodo(any(Todo.class))).thenReturn(updatedTodo);

        mockMvc.perform(patch(URL_PATH_VARIABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatedTodoRequest)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.title").value("test10"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shoudUpdate_NotFoundTodoTest() throws Exception {

        TodoRequest updatedTodoRequest = new TodoRequest("test10",true,1000);

        when(service.saveTodo(any(Todo.class))).thenReturn(new Todo(1L,"test10",true,1000));

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String exceptionMessage = "Todo not found";

        mockMvc.perform(patch(URL_PATH_VARIABLE_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatedTodoRequest)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TodoNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(),exceptionMessage));;
    }

    @Test
    public void deleteTodoTest() throws Exception {

        when(service.getTodoById(anyLong())).thenReturn(new Todo(1L,"test1",true,100));

        when(service.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete(URL_PATH_VARIABLE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shoulddelete_NotFoundTodoTest() throws Exception {

        when(service.getTodoById(anyLong())).thenReturn(new Todo(1L,"test1",true,100));

        when(service.existsById(anyLong())).thenReturn(false);

        String exceptionMessage = "Todo not found";

        mockMvc.perform(delete(URL_PATH_VARIABLE_2))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TodoNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(),exceptionMessage));
    }
}