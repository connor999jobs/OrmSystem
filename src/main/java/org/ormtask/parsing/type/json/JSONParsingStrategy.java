package org.ormtask.parsing.type.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.ormtask.table.Table;
import org.ormtask.source.FileReadWriteSource;
import org.ormtask.parsing.ParsingStrategy;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONParsingStrategy implements ParsingStrategy<FileReadWriteSource> {

    @Override
    @SneakyThrows
    public Table parseToTable(FileReadWriteSource content) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode treeNode = mapper.readTree(content.getContent());
        Map<Integer, Map<String, String>> result = buildTable(treeNode);
        return new Table(result);
    }

    private Map<Integer, Map<String, String>> buildTable(JsonNode treeNode) {
        Map<Integer,Map<String,String>> map = new LinkedHashMap<>();
        int index = 0;
        for (JsonNode each : treeNode){
            Map<String,String> item = buildRow(each);
            map.put(index,item);
            index++;
        }
        return map;
    }

    private Map<String, String> buildRow(JsonNode each) {
        Map<String, String> item = new LinkedHashMap<>();
        Iterator<Map.Entry<String,JsonNode>> iterator = each.fields();
        while (iterator.hasNext()){
            Map.Entry<String, JsonNode> next = iterator.next();
            item.put(next.getKey(),next.getValue().textValue());
        }
        return item;
    }
}
