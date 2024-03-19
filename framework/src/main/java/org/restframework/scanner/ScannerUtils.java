package org.restframework.scanner;

import org.antlr.v4.runtime.misc.Pair;
import org.restframework.web.WebApp;

import java.util.List;
import java.util.Optional;

import static org.restframework.web.WebApp.scannerApp;

public class ScannerUtils {
    public static Optional<FileRecord> with(String fileName) {
        for (List<FileRecord> fileRecords : scannerApp().getFiles().values()) {
            for (FileRecord record : fileRecords) {
                if (record.getName().equals(fileName)) {
                    return Optional.of(record);
                }
            }
        }
        return Optional.empty();
    }

}
