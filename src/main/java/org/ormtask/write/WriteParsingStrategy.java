package org.ormtask.write;

import org.ormtask.source.DataReadWriteSource;
import org.ormtask.source.FileReadWriteSource;

import java.util.List;

public interface WriteParsingStrategy {
    void write(List<?> objects);
}
