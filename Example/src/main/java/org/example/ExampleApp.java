package org.example;


import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.restframework.scanner.DirectoryScannerAdvanced;
import org.restframework.scanner.DirectoryScannerSimple;
import org.restframework.scanner.FileRecord;
import org.restframework.scanner.PackageScanner;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.EnableRestConfiguration;
import org.restframework.web.annotations.gen.GenDto;
import org.restframework.web.annotations.gen.GenModel;
import org.restframework.web.annotations.gen.GenProperties;
import org.restframework.web.annotations.gen.GenSpring;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.core.builders.MethodBuilder;
import org.restframework.web.core.builders.Modifier;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.MethodImplementations;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@EnableRestConfiguration(useCustomGenerationStrategy = true)
@GenProperties(basePackage = "org.example.test")
@GenDto
@GenModel(
        tableName = "test_table",
        fields = {
                @FieldData(name="fname"),
                @FieldData(name="index")
        }
)
@GenSpring()
@SpringBootApplication
public class ExampleApp {
    @SneakyThrows
    public static void main(String[] args) {
//        String rootPath = "D:\\Program Files (x86)\\Programming\\repositories\\SpringRestfull\\Example\\src\\main\\java\\org\\example\\";
//        PackageScanner scanner = new PackageScanner(new DirectoryScannerAdvanced(GenDto.class));
//        Map<String, List<FileRecord>> packageMap = scanner.scanPackages(rootPath);
//        for (String packageName : packageMap.keySet()) {
//            System.out.println("Package: " + packageName);
//            List<FileRecord> fileRecords = packageMap.get(packageName);
//            for (FileRecord record : fileRecords) {
//                System.out.println("   Name: " + record.getName() + ", Size: " + record.getSize() + " bytes");
//            }
//        }
        new WebApp(ExampleApp.class)
            .run(args);
    }

}