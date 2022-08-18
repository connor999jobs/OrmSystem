package org.ormtask.service;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.ormtask.table.Table;
import org.ormtask.source.DataReadWriteSource;
import org.ormtask.source.ConnectionReadWriteSource;
import org.ormtask.source.FileReadWriteSource;
import org.ormtask.parsing.type.database.DataBaseParsingStrategy;
import org.ormtask.parsing.ParsingStrategy;
import org.ormtask.parsing.type.csv.CSVParsingStrategy;
import org.ormtask.parsing.type.json.JSONParsingStrategy;
import org.ormtask.parsing.type.xml.XMLParsingStrategy;
import org.ormtask.write.WriteParsingStrategy;
import org.ormtask.write.csv.CsvWriteParsingStrategy;
import org.ormtask.write.json.JsonWriteParsingStrategy;
import org.ormtask.write.xml.XmlWriteParsingStrategy;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ORM implements ORMList {
    @Override
    @SneakyThrows
    public <T> List<T> readAll(DataReadWriteSource<?> inputSource, Class<T> cls) {
        Table table = convertToTable(inputSource);
        return convertTableToListOfClasses(table, cls);
    }



    private <T> Table convertToTable(DataReadWriteSource<T> dataReadWriteSource) {
        if (dataReadWriteSource instanceof ConnectionReadWriteSource){
            return new DataBaseParsingStrategy().parseToTable((ConnectionReadWriteSource) dataReadWriteSource);
        } else if (dataReadWriteSource instanceof FileReadWriteSource){
            return getStringParsingStrategy((FileReadWriteSource) dataReadWriteSource)
                    .parseToTable((FileReadWriteSource) dataReadWriteSource);
        } else
        {
            throw new UnsupportedOperationException("Unknown type " + dataReadWriteSource);
        }
    }

    private <T> List<T> convertTableToListOfClasses(Table table, Class<T> cls) {
        List<T> result = new ArrayList<>();
        for (int index = 0; index < table.size(); index++) {
            Map<String, String> row = table.getTableRowByIndex(index);
            T instance = reflectTableRowToClass(row, cls);
            result.add(instance);
        }
        return result;
    }

    @SneakyThrows
    private <T> T reflectTableRowToClass(Map<String, String> row, Class<T> cls) {
        T instance = cls.getDeclaredConstructor().newInstance();
        for (Field each: cls.getDeclaredFields()) {
            each.setAccessible(true);
            String value = row.get(each.getName());
            if (value != null) {
                each.set(instance, transformValueToFieldType(each, value));
            }
        }
        return instance;
    }

    private static Object transformValueToFieldType(Field field, String value) {
        Map<Class<?>, Function<String, Object>> typeToFunction = new LinkedHashMap<>();
        typeToFunction.put(String.class, s -> s);
        typeToFunction.put(Integer.class, Integer::parseInt);
        typeToFunction.put(Float.class, Float::parseFloat);
        typeToFunction.put(LocalDate.class, LocalDate::parse);
        typeToFunction.put(LocalDateTime.class, LocalDate::parse);
        typeToFunction.put(Long.class, Long::parseLong);
        typeToFunction.put(BigInteger.class, BigInteger::new);

        return typeToFunction.getOrDefault(field.getType(), type -> {
            throw new UnsupportedOperationException("Type is not supported by parser " + type);
        }).apply(value);
    }

    private ParsingStrategy<FileReadWriteSource> getStringParsingStrategy(FileReadWriteSource inputSource){
        String content = String.valueOf(inputSource.getContent());
        char firstChar =content.charAt(0);
        switch (firstChar) {
            case '{':
            case '[':
                return new JSONParsingStrategy();
            case '<':
                return new XMLParsingStrategy();
            default:
                return new CSVParsingStrategy();
        }
    }



    @Override
    public <T> void writeAll(DataReadWriteSource<?> content, List<T> object) {
        if(content instanceof FileReadWriteSource){
            WriteParsingStrategy strategy = getWriteParsingStrategy(content);
            writeValueToFile(strategy,object);
        }
    }

    private <T> void writeValueToFile(WriteParsingStrategy strategy, List<T> object) {
        strategy.write(object);
    }


    private WriteParsingStrategy getWriteParsingStrategy(DataReadWriteSource<?> content) {
        String contentType = FilenameUtils.getExtension(((FileReadWriteSource) content).getSource().getName());
        if (contentType.equals("json")){
            return new JsonWriteParsingStrategy();
        }
        else if (contentType.equals("xml")){
            return new XmlWriteParsingStrategy();
        }
        else {
            return new CsvWriteParsingStrategy();
        }
    }
}
