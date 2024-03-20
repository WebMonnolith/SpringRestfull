package org.restframework.scanner;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.AppRunner;
import org.restframework.web.core.RestApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public final class ScannerApplication implements RestApp<ScannerApplication> {

    private final Class<?> classContext;
    private final String rootFile;
    private final PackageScanner scanner;
    private Map<String, List<FileRecord>>  files;

    private ScannerApplication scan() {
        this.files = this.scanner.scanPackages(this.rootFile);
        return this;
    }

    @Override
    public ScannerApplication run() {
        this.scan();
        return this;
    }


    @Override
    public ScannerApplication run(String[] args) {
        this.scan();
        return this;
    }
}
