package org.ormtask.write.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.SneakyThrows;
import org.ormtask.write.WriteParsingStrategy;
import org.ormtask.write.json.JsonWriteParsingStrategy;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class XmlWriteParsingStrategy implements WriteParsingStrategy {


    private File file;
    @Override
    @SneakyThrows
    public void write(List<?> objectsList) {
        URL url = XmlWriteParsingStrategy.class.getClassLoader().getResource("reader.xml");
        assert url != null;
        File file = new File(url.toURI());
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JSR310Module());
        String xmlValue = mapper.writeValueAsString(objectsList);
        Files.write(Path.of(file.getPath()), xmlValue.getBytes());
    }
}
