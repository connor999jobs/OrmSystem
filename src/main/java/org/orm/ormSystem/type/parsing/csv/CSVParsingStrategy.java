package org.orm.ormSystem.type.parsing.csv;

import org.orm.ormSystem.table.Table;
import org.orm.ormSystem.transform.source.StringInputSource;
import org.orm.ormSystem.type.parsing.ParsingStrategy;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVParsingStrategy implements ParsingStrategy<StringInputSource> {

    public static final String DELIMITER = ",";
    public static final String COMMENT = "--";

    @Override
    public Table parseToTable(StringInputSource content) {
        List<String> lines = Arrays.asList(content.getContent().split(System.lineSeparator()));
        Map<Integer, String> mapping = buildMapping(lines.get(0));
        Map<Integer, Map<String, String>> result = buildTable(lines.subList(1, lines.size()), mapping);
        return new Table(result);
    }


    private Map<Integer, Map<String, String>> buildTable(List<String> subList, Map<Integer, String> mapping) {
        Map<Integer, Map<String, String>> result = new LinkedHashMap<>();
        for (int index = 0; index < subList.size(); index++){
            String line = subList.get(index);
            result.put(index, buildRow(mapping,line));
        }
        return result;
    }

    private Map<String, String> buildRow(Map<Integer, String> mapping, String line) {
        Map<String,String> nameValueMap =new LinkedHashMap<>();
        String[] rItems = splitLine(line);
        for (int rowIndex = 0; rowIndex < rItems.length; rowIndex++){
            String value = rItems[rowIndex];
            nameValueMap.put(mapping.get(rowIndex), value);
        }
        return nameValueMap;
    }

    private Map<Integer, String> buildMapping(String s) {
        Map<Integer,String> map = new LinkedHashMap<>();
        String[] array = splitLine(s);
        for (int i = 0; i < array.length; i++) {
            String value = array[i];
            if (value.contains(COMMENT)){
                value = value.split(COMMENT)[0];
            }
            map.put(i, value.trim());
        }
        return map;
    }

    private static String[] splitLine(String line) {
        return line.split(DELIMITER);
    }


}
