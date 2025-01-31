package org.example.controller;

import org.example.controller.strategy.SortByPriority;
import org.example.controller.strategy.TaskSortingStrategy;
import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.controller.validation.Impl.TaskValidation;
import org.example.controller.validation.TaskInterfaceValidation;
import org.example.model.Enum.StatusEnum;
import org.example.model.Task;
import org.example.repository.TaskDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskController {
    private final TaskInterfaceValidation taskValidation;
    private final TaskDAO database;
    private TaskSortingStrategy taskSortingStrategy;

    public TaskController(TaskDAO databaseTask) {
        this.database = databaseTask;
        this.taskValidation = new TaskValidation();
        this.taskSortingStrategy = new SortByPriority();
    }

    public void setSortingStrategy(TaskSortingStrategy taskSortingStrategy){
        this.taskSortingStrategy = taskSortingStrategy;
    }

    public ResultValidationEnum addTask(Task task) {
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return result;
        }
        database.insertTask(task);
        return result;
    }

    public ResultValidationEnum updateTask(Task task) {
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return result;
        }
        database.updateTask(task);
        return result;
    }

    public Task getTaskById(int taskId) {
        Task task = database.getTaskById(taskId);

        if (task == null) {
            return null;
        }
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return null;
        }
        return task;
    }

    public ResultValidationEnum deleteTask(int taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            database.deleteTaskById(taskId);
            return ResultValidationEnum.APPROVED;
        }
        return ResultValidationEnum.REJECTED;
    }

    public List<Task> getAllTasks(long userId) {
        List<Task> tasks = database.getTasksByUserId(userId);
        return taskSortingStrategy.sort(tasks);
    }

    public Map<StatusEnum, Long> getTaskCountByStatus(long userId) {
        List<Task> tasks = database.getTasksByUserId(userId);
        return tasks.stream().collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }

}
