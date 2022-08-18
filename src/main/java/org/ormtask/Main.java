package org.ormtask;

import org.ormtask.model.Person;
import org.ormtask.connection.ConnectionToDatabase;
import org.ormtask.model.PersonOrm;
import org.ormtask.service.ORM;
import org.ormtask.service.ORMList;
import org.ormtask.source.ConnectionReadWriteSource;
import org.ormtask.source.DataReadWriteSource;
import org.ormtask.source.FileReadWriteSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.List;

public class Main {

    private static final ORMList ORM = new ORM();
    private static final ConnectionToDatabase connect = new ConnectionToDatabase();


    public static void main(String[] args) throws URISyntaxException {

//        List<Person> result;
//        DataReadWriteSource<ResultSet> rw = new ConnectionReadWriteSource(connect.getConnection(), "person");
//        result = ORM.readAll(rw,Person.class);

        URL url = Main.class.getClassLoader().getResource("reader.xml");
        assert url != null;
        DataReadWriteSource<?> readWriteSource = new FileReadWriteSource(new File(url.toURI()));
        List<Person> personList = ORM.readAll(readWriteSource, Person.class);
        ORM.writeAll(readWriteSource, personList);
    }

}
