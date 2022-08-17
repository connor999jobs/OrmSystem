package org.ormtask.fileType;

import org.ormtask.table.Table;
import org.ormtask.source.DataReadWriteSource;

public interface ParsingStrategy<T extends DataReadWriteSource<?>> {
    Table parseToTable(T content);
}
