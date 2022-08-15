package org.orm;

import org.orm.modele.PersonOrm;
import org.orm.ormSystem.connect.ConnectionToDatabase;
import org.orm.ormSystem.transform.ORM;
import org.orm.ormSystem.transform.ORMList;
import org.orm.ormSystem.transform.source.DatabaseInputSource;
import org.orm.ormSystem.type.parsing.database.DataBaseParsingStrategy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.util.List;

public class Main {


    public static void main(String[] args) throws URISyntaxException, IOException {
//        URL url = Main.class.getClassLoader().getResource("reader.csv");
//        String content = FileUtils.readFileToString(new File(url.toURI()), StandardCharsets.UTF_8);
        ORMList interfaceList =new ORM();
//        List<PersonOrm> result =interfaceList.transformFile(new StringInputSource(content), PersonOrm.class);


        ConnectionToDatabase connection = new ConnectionToDatabase();
        ResultSet set = connection.getPerson();

        List<PersonOrm> list = interfaceList.transformFile(new DatabaseInputSource(set), PersonOrm.class);
        System.out.println(list);





     }

}
