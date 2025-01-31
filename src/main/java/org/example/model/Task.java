package org.example.model;

import org.example.model.Enum.PriorityEnum;
import org.example.model.Enum.StatusEnum;

import java.time.LocalDate;

public class Task {
    private long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private StatusEnum status;
    private PriorityEnum priority;
    private String category;
    private long userId;

    public Task(long id, String title, String description, LocalDate dueDate,
                StatusEnum status, PriorityEnum priority, String category, long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.userId = userId;
    }

    public Task(String title, String description, long userId, LocalDate dueDate,
                StatusEnum status, PriorityEnum priority, String category) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Task {" +
                "\n  Category: '" + category + '\'' +
                ",\n  Priority: " + priority +
                ",\n  Status: " + status +
                ",\n  Due Date: " + dueDate +
                ",\n  Description: '" + description + '\'' +
                ",\n  Title: '" + title + '\'' +
                "\n}";
    }
}
