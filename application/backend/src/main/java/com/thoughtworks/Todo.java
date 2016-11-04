package com.thoughtworks;

import java.net.URI;

public class Todo {
    private String title;
    private Boolean completed;
    private URI url;
    private Integer order;

    public Todo() {
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Todo patchWith(Todo todo) {
        if (todo.getTitle() != null) {
            this.title = todo.getTitle();
        }

        if (todo.isCompleted() != null) {
            this.completed = todo.isCompleted();
        }

        if (todo.getOrder() != null) {
            this.order = todo.getOrder();
        }

        return this;
    }
}
