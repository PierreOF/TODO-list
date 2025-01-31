package org.example.controller.strategy;

import org.example.model.Task;

import java.util.Comparator;
import java.util.List;

public class SortByPriority implements TaskSortingStrategy {
    @Override
    public List<Task> sort(List<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getPriority))
                .toList();
    }
}