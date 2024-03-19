package org.restframework.scanner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class FileRecord {
    private final String name;
    private final long size;
    private boolean updateAble = false;

    public FileRecord set(boolean update) {
        this.updateAble = update;
        return this;
    }
}
