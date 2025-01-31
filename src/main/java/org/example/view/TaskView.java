package org.example.view;

import org.example.controller.TaskController;
import org.example.controller.UserController;
import org.example.controller.strategy.*;
import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.model.Enum.PriorityEnum;
import org.example.model.Enum.StatusEnum;
import org.example.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskView {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskController taskController;
    private final UserController userController;
    private final long userId;
    private TaskSortingStrategy currentSortingStrategy;

    public TaskView(TaskController taskController, long userId, UserController userController) {
        this.taskController = taskController;
        this.userController = userController;
        this.userId = userId;
        this.currentSortingStrategy = new SortByPriority();
    }

    public void menu() {
        int option;
        do {
            showMenu();
            option = getOptionFromUser();

            switch (option) {
                case 1 -> displayTasks();
                case 2 -> addNewTask();
                case 3 -> deleteTask();
                case 4 -> updateTask();
                case 5 -> changeSortingStrategy();
                case 6 -> displayTasksByStatus();
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 7);
    }

    private void showMenu() {
        System.out.println("\n---- Task Menu ----");
        System.out.println("1. Show tasks");
        System.out.println("2. Create a task");
        System.out.println("3. Delete a task");
        System.out.println("4. Update a task");
        System.out.println("5. Change sorting method");
        System.out.println("6. Display tasks by status (TODO, Doing, Done)");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private int getOptionFromUser() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid option.");
            return -1;
        }
    }

    private void displayTasks() {
        taskController.setSortingStrategy(currentSortingStrategy);
        List<Task> tasks = taskController.getAllTasks(userId);

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            tasks.forEach(this::showTask);
        }
    }

    private void changeSortingStrategy() {
        System.out.println("\n---- Change Sorting Method ----");
        System.out.println("1. Sort by priority");
        System.out.println("2. Sort by completion status");
        System.out.println("3. Sort by category");
        System.out.println("4. Sort by due date");
        System.out.print("Choose an option: ");

        int sortOption = getOptionFromUser();
        currentSortingStrategy = switch (sortOption) {
            case 1 -> new SortByPriority();
            case 2 -> new SortByConclusionStatus();
            case 3 -> new SortByCategory();
            case 4 -> new SortByDate();
            default -> {
                System.out.println("Invalid option. Keeping current sorting method.");
                yield currentSortingStrategy;
            }
        };
        System.out.println("Sorting method changed successfully.");
    }

    private void addNewTask() {
        Task newTask = getNewTaskDetails();
        if (newTask != null) {
            taskController.addTask(newTask);
            System.out.println("Task added successfully.");
        }
    }

    private void deleteTask() {
        int taskId = getTaskIdFromUser();
        taskController.deleteTask(taskId);
        System.out.println("Task deleted successfully.");
    }

    private void updateTask() {
        int taskId = getTaskIdFromUser();
        Task task = taskController.getTaskById(taskId);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        showTask(task);
        Task updatedTask = setNewDataToTask(task);

        if (updatedTask != null && taskController.updateTask(updatedTask) == ResultValidationEnum.APPROVED) {
            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Error updating task.");
        }
    }

    private Task getNewTaskDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Priority (LOW, NORMAL, IMPORTANT, VERY_IMPORTANT, CRITICAL): ");
        String priority = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("Status (Todo, Doing, Done): ");
        String status = scanner.nextLine();
        System.out.print("Due date (yyyy-MM-dd): ");
        try {
            LocalDate dateTime = LocalDate.parse(scanner.nextLine(), formatter);
            return new Task(title, description, userId, dateTime, StatusEnum.getByDescription(status), PriorityEnum.getPriorityEnum(priority), category);
        } catch (Exception e) {
            System.out.println("Invalid date. Please use the correct format.");
            return null;
        }
    }

    private int getTaskIdFromUser() {
        System.out.print("Task ID: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return -1;
        }
    }

    private Task setNewDataToTask(Task task) {
        System.out.println("\n---- Update Task ----");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Priority (LOW, NORMAL, IMPORTANT, VERY_IMPORTANT, CRITICAL): ");
        String priority = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("Status (Todo, Doing, Done): ");
        String status = scanner.nextLine();
        System.out.print("Due date (yyyy-MM-dd): ");

        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(PriorityEnum.getPriorityEnum(priority));
        task.setCategory(category);
        task.setStatus(StatusEnum.getByDescription(status));

        try {
            LocalDate dateTime = LocalDate.parse(scanner.nextLine(), formatter);
            task.setDueDate(dateTime);
            return task;
        } catch (Exception e) {
            System.out.println("Invalid date. Please use the correct format.");
            return null;
        }
    }

    private void showTask(Task task) {
        System.out.println("\n---- Task Details ----");
        System.out.println("ID: " + task.getId());
        System.out.println("Title: " + task.getTitle());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Priority: " + task.getPriority());
        System.out.println("Category: " + task.getCategory());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Due Date: " + task.getDueDate());
    }

    private void displayTasksByStatus() {
        Map<StatusEnum, Long> taskCounts = taskController.getTaskCountByStatus(userId);

        System.out.println("\n---- Task Count by Status ----");
        taskCounts.forEach((status, count) ->
                System.out.println(status.getDescription() + ": " + count));

        System.out.println("\n---- Tasks by Status ----");
        for (StatusEnum status : StatusEnum.values()) {
            System.out.println("\nStatus: " + status.getDescription());
            List<Task> tasksByStatus = taskController.getAllTasks(userId).stream()
                    .filter(task -> task.getStatus() == status)
                    .toList();
            tasksByStatus.forEach(this::showTask);
        }
    }
}
