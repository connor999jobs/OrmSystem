package org.orm.ormSystem.type.parsing.xml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.SneakyThrows;
import org.orm.ormSystem.table.Table;
import org.orm.ormSystem.type.parsing.ParsingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMLParsingStrategy implements ParsingStrategy {

    @Override
    @SneakyThrows
    public Table parseToTable(String content) {
        XmlMapper parser =new XmlMapper();
        parser.registerModule(new JSR310Module());
        JsonNode node = parser.readTree(content);
        Map<Integer, Map<String, String>> result = buildTable(node);


        return new Table(result);
    }

    private Map<Integer, Map<String, String>> buildTable(JsonNode treeNode) {
        Map<Integer,Map<String,String>> xmlMap = new LinkedHashMap<>();
        int index = 0;
        for (JsonNode each : treeNode){
            Map<String,String> item = bulidRow(each);
            xmlMap.put(index,item);
            index++;
        }
        return xmlMap;
    }

    private Map<String, String> bulidRow(JsonNode each) {
        Map<String, String> item = new LinkedHashMap<>();
        Iterator<Map.Entry<String,JsonNode>> iterator = each.fields();
        while (iterator.hasNext()){
            Map.Entry<String, JsonNode> next = iterator.next();
            item.put(next.getKey(),next.getValue().textValue());
        }
        return item;
    }
}
