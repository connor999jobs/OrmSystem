package org.orm.ormSystem;

import java.io.File;
import java.util.List;

public interface ORMList {
    public <T>List<T> transformFile(File file, Class<T> csl);
}
