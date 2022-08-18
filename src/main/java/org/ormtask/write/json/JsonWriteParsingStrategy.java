package org.ormtask.write.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ormtask.source.FileReadWriteSource;
import org.ormtask.write.WriteParsingStrategy;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


@RequiredArgsConstructor
public class JsonWriteParsingStrategy implements WriteParsingStrategy {
    private final FileReadWriteSource filePath;

    @Override
    @SneakyThrows
    public void write(List<?> objectsList) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        String json = objectMapper.writeValueAsString(objectsList);
        Files.write(Path.of(filePath.getContent()), json.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
