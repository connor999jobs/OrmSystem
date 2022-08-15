package org.orm.ormSystem.transform;

import org.orm.modele.PersonOrm;
import org.orm.ormSystem.transform.source.DataInputSource;
import org.orm.ormSystem.transform.source.DatabaseInputSource;

import java.io.File;
import java.util.List;

public interface ORMList {
    public <T>List<T> transformFile(DataInputSource content, Class<T> csl);

}
