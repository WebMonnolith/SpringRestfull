package org.restframework.scanner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileRecord {
    private final String name;
    private final long size;
}
