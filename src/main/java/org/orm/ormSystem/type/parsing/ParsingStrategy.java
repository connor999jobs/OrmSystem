package org.orm.ormSystem.type.parsing;

import org.orm.ormSystem.table.Table;
import org.orm.ormSystem.transform.source.DataInputSource;

public interface ParsingStrategy<T extends DataInputSource> {
    Table parseToTable(T content);
}
