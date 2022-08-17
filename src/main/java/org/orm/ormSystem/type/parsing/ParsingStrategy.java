package org.orm.ormSystem.type.parsing;

import org.orm.ormSystem.table.Table;
import org.orm.ormSystem.transform.source.DataReadWriteSource;

public interface ParsingStrategy<T extends DataReadWriteSource> {
    Table parseToTable(T content);
}
