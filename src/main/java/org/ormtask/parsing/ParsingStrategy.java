package org.ormtask.parsing;

import org.ormtask.table.Table;
import org.ormtask.source.DataReadWriteSource;

public interface ParsingStrategy<T extends DataReadWriteSource<?>> {
    Table parseToTable(T content);
}
