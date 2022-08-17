package org.ormtask;

import org.ormtask.model.Person;
import org.ormtask.connection.ConnectionToDatabase;
import org.ormtask.service.ORM;
import org.ormtask.service.ORMList;
import org.ormtask.source.ConnectionReadWriteSource;
import org.ormtask.source.DataReadWriteSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;

public class Main {

    private static final ORMList ORM = new ORM();
    private static final ConnectionToDatabase connect = new ConnectionToDatabase();


    public static void main(String[] args)  {

        List<Person> result;
        DataReadWriteSource<ResultSet> rw = new ConnectionReadWriteSource(connect.getConnection(), "person");
        result = ORM.readAll(rw,Person.class);
    }

}
