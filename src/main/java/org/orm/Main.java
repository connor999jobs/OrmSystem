package org.orm;

import org.orm.modele.PersonOrm;
import org.orm.ormSystem.ORM;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        URL url = Main.class.getClassLoader().getResource("readers.xml");
        List<PersonOrm> list = new ORM().transformFile(new File(url.toURI()), PersonOrm.class);
    }
}
