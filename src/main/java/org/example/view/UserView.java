package org.example.view;

import org.example.controller.TaskController;
import org.example.controller.UserController;
import org.example.controller.validation.Enum.ResultValidationEnum;
import org.example.model.User;
import org.example.repository.TaskDAO;

import java.sql.Connection;
import java.util.Scanner;

public class UserView {
    private final UserController userController;
    private final Scanner scanner;
    private final TaskDAO taskDAO;

    public UserView(Connection connection) {
        this.userController = new UserController(connection);
        this.scanner = new Scanner(System.in);
        this.taskDAO = new TaskDAO(connection);
    }

    public void menu() {
        int option = 0;
        do {
            System.out.println("---- User Menu ----");
            System.out.println("1. Login");
            System.out.println("2. Register new user");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            if (scanner.hasNextLine()) {
                try {
                    option = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option.\n");
                    continue;
                }
            } else {
                System.out.println("No input detected. Exiting...");
                break;
            }

            switch (option) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 3);
    }


    private void login() {
        System.out.println("---- User Login ----");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User userDTO = new User(email, password);

        User user = userController.login(userDTO);

        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName() + "!");
            TaskController taskController = new TaskController(taskDAO);
            TaskView taskView = new TaskView(taskController, user.getId(), userController);
            taskView.menu();
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void register() {
        System.out.println("---- Register New User ----");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User userDTO = new User(name, email, password);

        ResultValidationEnum resultValidation = userController.register(userDTO);

        if (resultValidation == ResultValidationEnum.APPROVED) {
            System.out.println("User registered successfully!");
            return;
        }
        System.out.println("Error registering user.");
    }
}
