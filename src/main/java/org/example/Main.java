package org.example;

import org.example.repository.Sqlconnection;

import org.example.view.UserView;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Sqlconnection.createTables();

        Connection conn = Sqlconnection.connect();

        UserView usuarioView = new UserView(conn);
        usuarioView.menu();

        Sqlconnection.close();
    }
}