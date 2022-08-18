package org.ormtask.write.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ormtask.source.DataReadWriteSource;
import org.ormtask.source.FileReadWriteSource;
import org.ormtask.write.WriteParsingStrategy;
import org.ormtask.write.json.JsonWriteParsingStrategy;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


@RequiredArgsConstructor
public class XmlWriteParsingStrategy implements WriteParsingStrategy {

    private final FileReadWriteSource source;
    @Override
    @SneakyThrows
    public void write(List<?> objectsList) {
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JSR310Module());
        String xmlValue = mapper.writeValueAsString(objectsList);
        Files.write(Path.of(source.getContent()), xmlValue.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
