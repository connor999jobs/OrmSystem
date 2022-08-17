package org.orm.ormSystem.type.parsing.database;

import lombok.SneakyThrows;
import org.orm.ormSystem.table.Table;
import org.orm.ormSystem.transform.source.ConnectionReadWriteSource;
import org.orm.ormSystem.type.parsing.ParsingStrategy;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataBaseParsingStrategy implements ParsingStrategy<ConnectionReadWriteSource> {

    @Override
    public Table parseToTable(ConnectionReadWriteSource content) {
        ResultSet rs = content.getContent();
        Map<Integer, Map<String, String>> resultSet = buildTable(rs);
        return new Table(resultSet);
    }


    @SneakyThrows
    private Map<Integer, Map<String, String>> buildTable(ResultSet rs) {
        ResultSetMetaData metaData = rs.getMetaData();
        Map<Integer, Map<String, String>> result = new LinkedHashMap<>();
        int rowId = 0;

        while (rs.next()){
            Map<String, String> row =new LinkedHashMap<>();
            for (int index = 1; index < metaData.getColumnCount(); index++) {
                row.put(metaData.getColumnName(index), rs.getString(index));
            }
            result.put(rowId, row);
            rowId++;
        }
        return result;
    }
}
