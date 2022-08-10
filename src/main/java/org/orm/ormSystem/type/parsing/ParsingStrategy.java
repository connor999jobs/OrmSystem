package org.orm.ormSystem.type.parsing;

import org.orm.ormSystem.table.Table;

public interface ParsingStrategy {
    Table parseToTable(String content);
}
