package org.orm;

import lombok.SneakyThrows;
import org.orm.modele.Person;
import org.orm.modele.PersonOrm;
import org.orm.ormSystem.connect.ConnectionToDatabase;
import org.orm.ormSystem.transform.ORM;
import org.orm.ormSystem.transform.ORMList;
import org.orm.ormSystem.transform.source.ConnectionReadWriteSource;
import org.orm.ormSystem.transform.source.DataReadWriteSource;
import org.orm.ormSystem.type.parsing.csv.CSVParsingStrategy;
import org.orm.ormSystem.type.parsing.database.DataBaseParsingStrategy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {

    private static final ORMList ORM = new ORM();
    private static final ConnectionToDatabase connect = new ConnectionToDatabase();


    public static void main(String[] args) throws URISyntaxException, IOException, SQLException {

        List<Person> result;
        DataReadWriteSource<ResultSet> rw = new ConnectionReadWriteSource(connect.getConnection(), "person");
        result = ORM.readAll(rw,Person.class);

    }

}
