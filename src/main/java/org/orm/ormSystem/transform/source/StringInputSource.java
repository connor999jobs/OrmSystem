package org.orm.ormSystem.transform.source;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StringInputSource implements DataInputSource {
    public final String content;
}
