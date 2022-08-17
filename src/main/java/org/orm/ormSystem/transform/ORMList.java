package org.orm.ormSystem.transform;

import org.orm.ormSystem.transform.source.DataReadWriteSource;

import java.util.List;

public interface ORMList {
    <T>List<T> readAll(DataReadWriteSource<?> content, Class<T> csl);

    <T> void writeAll(DataReadWriteSource<?> content, List<T> object);

}
