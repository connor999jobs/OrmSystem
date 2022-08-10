package org.orm.ormSystem.table;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Table {
    private final Map<Integer, Map<String, String>> table;

    private String getCell(int row, String columnName) {
        Map<String, String> nameToCell = table.get(row);
        if (nameToCell != null) {
            return nameToCell.get(columnName);
        }
        return null;
    }

    public int size() {
        return table.size();
    }

    public Map<String, String> getTableRowByIndex(int row) {
        Map<String, String> result = table.get(row);
        return result == null ? null : new LinkedHashMap<>(result);
    }


}
