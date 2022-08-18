package org.ormtask.write.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.SneakyThrows;
import org.ormtask.Main;
import org.ormtask.write.WriteParsingStrategy;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class JsonWriteParsingStrategy implements WriteParsingStrategy {
    @Override
    @SneakyThrows
    public void write(List<?> objectsList) {
        URL url = JsonWriteParsingStrategy.class.getClassLoader().getResource("reader.json");
        assert url != null;
        File file = new File(url.toURI());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        String json = objectMapper.writeValueAsString(objectsList);
        Files.write(Path.of(file.getPath()), json.getBytes(), StandardOpenOption.APPEND);
    }
}
