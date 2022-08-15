package org.orm.ormSystem.transform.source;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;

@RequiredArgsConstructor
@Getter
public class DatabaseInputSource implements DataInputSource {
    public final ResultSet resultSet;
}
