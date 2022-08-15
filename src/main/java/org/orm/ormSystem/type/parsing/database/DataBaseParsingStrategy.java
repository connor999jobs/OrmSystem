package org.orm.ormSystem.type.parsing.database;

import lombok.SneakyThrows;
import org.orm.modele.PersonOrm;
import org.orm.ormSystem.table.Table;
import org.orm.ormSystem.transform.source.DatabaseInputSource;
import org.orm.ormSystem.type.parsing.ParsingStrategy;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataBaseParsingStrategy implements ParsingStrategy<DatabaseInputSource> {



    @Override
    public Table parseToTable(DatabaseInputSource content) {
        ResultSet rs = content.getResultSet();
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
            for (int index = 0; index < metaData.getColumnCount(); index++) {
                row.put(metaData.getColumnLabel(rowId), rs.getString(index));
            }
            result.put(rowId, row);
        }
        return result;
    }
}
