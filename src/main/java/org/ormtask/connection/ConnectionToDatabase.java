package org.ormtask.connection;

import lombok.SneakyThrows;

import java.sql.*;

public class ConnectionToDatabase {
    private static final String url = "jdbc:postgresql://localhost:5432/OrmSystem";
    private static final String username = "postgres";
    private static final String password = "Vadym280576";


    @SneakyThrows
    public Connection getConnection() {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}
