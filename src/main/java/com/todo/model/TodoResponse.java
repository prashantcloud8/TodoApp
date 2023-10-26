package com.todo.model;


public class TodoResponse {
    private Long id;
    private String title;
    private boolean completed;
    private Integer order;
    private String url;

    public TodoResponse(Long id, String title, boolean completed, Integer order, String url) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.order = order;
        this.url = url;
    }

    public TodoResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Integer getOrder() {
        return order;
    }

    public String getUrl() {
        return url;
    }
}

