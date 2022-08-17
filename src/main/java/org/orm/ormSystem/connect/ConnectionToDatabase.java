package org.orm.ormSystem.connect;

import com.github.vldrus.sql.rowset.CachedRowSetWrapper;
import lombok.SneakyThrows;
import org.orm.modele.PersonOrm;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
