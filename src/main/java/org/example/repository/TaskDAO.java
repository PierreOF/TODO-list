package org.example.repository;

import org.example.model.Enum.PriorityEnum;
import org.example.model.Enum.StatusEnum;
import org.example.model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection;

    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    public void deleteTasksByUserId(int userId) {
        String sql = "DELETE FROM tasks WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTaskById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, priority = ?, due_date = ?, category = ? WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus().getDescription());
            stmt.setString(4, task.getPriority().getDescription());
            stmt.setTimestamp(5, Timestamp.valueOf(task.getDueDate().atStartOfDay()));
            stmt.setString(6, task.getCategory());
            stmt.setLong(7, task.getId());
            stmt.setLong(8, task.getUserId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Task updated successfully! Task ID: " + task.getId());
                System.out.println("New task data: " + task);
            } else {
                System.out.println("No task was updated. Please check the task ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating task", e);
        }
    }

    public Integer insertTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, status, priority, due_date, user_id, category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus().getDescription());
            stmt.setString(4, task.getPriority().getDescription());
            stmt.setTimestamp(5, Timestamp.valueOf(task.getDueDate().atStartOfDay()));
            stmt.setLong(6, task.getUserId());
            stmt.setString(7, task.getCategory());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert task, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to insert task, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> getTasksByUserId(long userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String status = rs.getString("status");
                String priority = rs.getString("priority");
                LocalDate dueDate = rs.getTimestamp("due_date").toLocalDateTime().toLocalDate();
                String category = rs.getString("category");

                tasks.add(new Task(id, title, description, dueDate, StatusEnum.getByDescription(status), PriorityEnum.getPriorityEnum(priority), category, userId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public Task getTaskById(long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                String status = rs.getString("status");
                String priority = rs.getString("priority");
                LocalDate dueDate = rs.getTimestamp("due_date").toLocalDateTime().toLocalDate();
                String category = rs.getString("category");
                int userId = rs.getInt("user_id");
                return new Task(id, title, description, dueDate, StatusEnum.getByDescription(status), PriorityEnum.getPriorityEnum(priority), category, userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
