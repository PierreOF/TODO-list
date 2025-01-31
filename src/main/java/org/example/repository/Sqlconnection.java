package org.example.repository;

import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Sqlconnection {
    private static final String DATABASE_URL = "jdbc:sqlite:task-manager.db";
    private static final Logger logger = Logger.getLogger(Sqlconnection.class.getName());
    private static Connection connection;

    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "password TEXT NOT NULL"
                + ");";

        String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"  // nome -> title
                + "description TEXT NOT NULL,"
                + "status TEXT NOT NULL,"
                + "priority TEXT NOT NULL,"
                + "due_date TIMESTAMP NOT NULL,"  // data_termino -> due_date
                + "user_id INTEGER NOT NULL,"
                + "category TEXT NOT NULL,"
                + "FOREIGN KEY(user_id) REFERENCES users(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createTasksTable);
            logger.info("Tables created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteDatabase() {
        File dbFile = new File("task-manager.db");
        if (dbFile.delete()) {
            logger.info("Database deleted successfully");
        } else {
            logger.warning("Failed to delete the database");
        }
    }
}
