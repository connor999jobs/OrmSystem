package org.ormtask.parsing.type.xml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.SneakyThrows;
import org.ormtask.table.Table;
import org.ormtask.source.FileReadWriteSource;
import org.ormtask.parsing.ParsingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMLParsingStrategy implements ParsingStrategy<FileReadWriteSource> {

    @Override
    @SneakyThrows
    public Table parseToTable(FileReadWriteSource content) {
        XmlMapper parser =new XmlMapper();
        parser.registerModule(new JSR310Module());
        JsonNode node = parser.readTree(content.getContent());
        Map<Integer, Map<String, String>> result = buildTable(node);

        return new Table(result);
    }

    private Map<Integer, Map<String, String>> buildTable(JsonNode treeNode) {
        Map<Integer,Map<String,String>> xmlMap = new LinkedHashMap<>();
        int index = 0;
        for (JsonNode each : treeNode){
            Map<String,String> item = buildRow(each);
            xmlMap.put(index,item);
            index++;
        }
        return xmlMap;
    }

    private Map<String, String> buildRow(JsonNode each) {
        Map<String, String> xmlItem = new LinkedHashMap<>();
        Iterator<Map.Entry<String,JsonNode>> xmlIterator = each.fields();
        while (xmlIterator.hasNext()){
            Map.Entry<String, JsonNode> next = xmlIterator.next();
            xmlItem.put(next.getKey(),next.getValue().textValue());
        }
        return xmlItem;
    }
}
