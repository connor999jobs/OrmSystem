package org.orm.ormSystem.transform;

import lombok.SneakyThrows;
import org.orm.ormSystem.table.Table;
import org.orm.ormSystem.transform.source.DataInputSource;
import org.orm.ormSystem.transform.source.DatabaseInputSource;
import org.orm.ormSystem.transform.source.StringInputSource;
import org.orm.ormSystem.type.parsing.database.DataBaseParsingStrategy;
import org.orm.ormSystem.type.parsing.ParsingStrategy;
import org.orm.ormSystem.type.parsing.csv.CSVParsingStrategy;
import org.orm.ormSystem.type.parsing.json.JSONParsingStrategy;
import org.orm.ormSystem.type.typeEnum.FileContentType;
import org.orm.ormSystem.type.parsing.xml.XMLParsingStrategy;

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

    public FileContentType contentType;
    public ParsingStrategy parsingStrategy;

    @Override
    @SneakyThrows
    public <T> List<T> transformFile(DataInputSource inputSource, Class<T> cls) {
        Table table = convertToTable(inputSource);
        return convertTableToListOfClasses(table, cls);
    }

    private Table convertToTable(DataInputSource dataInputSource) {
        if (dataInputSource instanceof DatabaseInputSource){
            return new DataBaseParsingStrategy().parseToTable((DatabaseInputSource) dataInputSource);
        } else if (dataInputSource instanceof StringInputSource){
            return getStringParsingStrategy((StringInputSource) dataInputSource)
                    .parseToTable((StringInputSource) dataInputSource);
        } else
        {
            throw new UnsupportedOperationException("Unknown type " + dataInputSource);
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

    private ParsingStrategy<StringInputSource> getStringParsingStrategy(StringInputSource inputSource){
        String content =inputSource.getContent();
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
}
