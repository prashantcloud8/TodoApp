package com.todo.entity;

import com.todo.controller.TodoController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private boolean completed;
    private Integer orderNumber;

    public Todo(Long id, String title, boolean completed, Integer orderNumber) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.orderNumber = orderNumber;
    }

    public Todo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUrl() {
        return linkTo(TodoController.class).slash(this.getId()).withSelfRel().getHref();
    }
}
