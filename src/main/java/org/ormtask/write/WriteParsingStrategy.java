package org.ormtask.write;

import java.util.List;

public interface WriteParsingStrategy {
    void write(List<?> objects);
}
