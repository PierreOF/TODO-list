package org.example.controller.strategy;

import org.example.model.Task;

import java.util.List;

public interface TaskSortingStrategy {
    List<Task> sort(List<Task> tasks);
}
