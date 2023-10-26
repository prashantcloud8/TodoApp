package com.todo.model;

public class TodoRequest {
    private String title;
    private Boolean completed;
    private Integer order;

    public TodoRequest(String title, Boolean completed, Integer order) {
        this.title = title;
        this.completed = completed;
        this.order = order;
    }

    public TodoRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
