package org.ormtask.service;

import org.ormtask.source.DataReadWriteSource;

import java.util.List;

public interface ORMList {
    <T>List<T> readAll(DataReadWriteSource<?> content, Class<T> csl);

    <T> void writeAll(DataReadWriteSource<?> content, List<T> object);

}
