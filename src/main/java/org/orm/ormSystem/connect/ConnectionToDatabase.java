package org.orm.ormSystem.connect;

import lombok.SneakyThrows;
import org.orm.modele.PersonOrm;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConnectionToDatabase {
    private static final String url = "jdbc:postgresql://localhost:5432/OrmSystem";
    private static final String username = "postgres";
    private static final String password = "Vadym280576";
    private static final String query = "select * from person";


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


    @SneakyThrows
    public ResultSet getPerson(){
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<PersonOrm> newList = new ArrayList<>();

        while (resultSet.next()){
            String name = resultSet.getString(1);
            String surname = resultSet.getString(2);
            Integer age = resultSet.getInt(3);
            LocalDate dateOfBirth = resultSet.getDate(4).toLocalDate();
            String gender = resultSet.getString(5);
            String company = resultSet.getString(6);
            String position = resultSet.getString(7);
            Float salary = resultSet.getFloat(8);

            newList.add(new PersonOrm(name,surname,age,dateOfBirth,gender,company,position,salary));
        }

        return resultSet;
    }
}
