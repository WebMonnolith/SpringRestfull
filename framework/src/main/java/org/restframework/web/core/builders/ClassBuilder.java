package org.restframework.web.core.builders;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restframework.scanner.FileRecord;
import org.restframework.web.annotations.markers.UpdateComponent;
import org.restframework.web.core.templates.ClassTypes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.restframework.scanner.ScannerUtils.with;
import static org.restframework.web.core.helpers.FileHelper.NO_DIR;
import static org.restframework.web.core.helpers.FileHelper.fileExists;

@Slf4j
public final class ClassBuilder implements Builder, BuilderUtils {

    private final String name;
    private final StringBuilder classDefinition;

    public ClassBuilder(
            @NotNull String name,
            @NotNull String basePackage,
            @NotNull String @NotNull [] annotations,
            @NotNull ClassTypes classType)
    {
        this.name = name;
        this.classDefinition = new StringBuilder("package "+basePackage+";\n\n")
                .append("import org.restframework.web.core.templates.*;\n")
                .append("import java.util.*;\n");

        for (String annotation : annotations)
            this.addAnnotation(annotation);

        this.classDefinition
                .append(classType.getValue()).append(name);
    }

    public ClassBuilder(
            @NotNull String name,
            @NotNull String basePackage,
            @NotNull String @NotNull [] annotations,
            @NotNull ClassTypes classType,
            @NotNull String[] imports)
    {
        this.name = name;
        this.classDefinition = new StringBuilder("package "+basePackage+";\n\n");

        for (String dependency : imports)
            this.classDefinition.append(dependency).append(";\n");
        this.classDefinition.append("\n");

        for (String annotation : annotations)
            this.addAnnotation(annotation);

        this.classDefinition
                .append(classType.getValue()).append(name);
    }

    @Override
    public <T> void addAnnotation(@NotNull T annotation) {
        classDefinition.append("@").append(annotation).append("\n");
    }

    @Override
    public void addInterface(@NotNull Class<?> clazz) {
        classDefinition
                .append(" implements ")
                .append(clazz.getSimpleName()).append(" {\n");
    }

    @Override
    public void addInterface(@NotNull Class<?> clazz, String @NotNull ... genericTypes) {
        classDefinition
                .append(" implements ")
                .append(clazz.getSimpleName());

        if (genericTypes.length > 0) {
            classDefinition.append("<");
            for (int i = 0; i < genericTypes.length; i++) {
                classDefinition.append(genericTypes[i]);
                if (i < genericTypes.length - 1)
                    classDefinition.append(", ");
            }
            classDefinition.append(">");
        }

        classDefinition.append(" {\n");
    }

    @Override
    public void addExtension(@NotNull Class<?> clazz) {
        classDefinition
                .append(" extends ")
                .append(clazz.getSimpleName()).append(" {\n");
    }

    @Override
    public void addExtension(@NotNull Class<?> clazz, String @NotNull ... genericTypes) {
        classDefinition
                .append(" extends ")
                .append(clazz.getSimpleName());

        if (genericTypes.length > 0) {
            classDefinition.append("<");
            for (int i = 0; i < genericTypes.length; i++) {
                classDefinition.append(genericTypes[i]);
                if (i < genericTypes.length - 1)
                    classDefinition.append(", ");
            }
            classDefinition.append(">");
        }

        classDefinition.append(" {\n");
    }

    @Override
    public void addMethod(MethodBuilder method) {
        classDefinition.append(method.getDefinition());
    }

    @Override
    public void addField(FieldBuilder builder) {
        this.classDefinition.append(builder.getDefinition());
    }

    public ClassBuilder prepareBuild(@Nullable UpdateComponent updateAnnotation) {


        return this;
    }

    @Override
    public void build(@NotNull String filePath, @NotNull String dir) {
        /*
        * TODO Add a way to handle file generation errors
        *  java.io.FileNotFoundException: D:\Program Files (x86)\Programming\repositories\SpringRestfull\Example\src\main\java\org\example\test\test1\controller\Test1Controller.java (The system cannot find the path specified)
            at java.base/java.io.FileOutputStream.open0(Native Method)
            at java.base/java.io.FileOutputStream.open(FileOutputStream.java:293)
            at java.base/java.io.FileOutputStream.<init>(FileOutputStream.java:235)
            at java.base/java.io.FileOutputStream.<init>(FileOutputStream.java:184)
            at java.base/java.io.FileWriter.<init>(FileWriter.java:96)
            at org.restframework.web.core.builders.ClassBuilder.build(ClassBuilder.java:150)
            at org.restframework.web.core.generators.MvcGenerator.generateClasses(MvcGenerator.java:82)
            at org.restframework.web.WebApp.generate(WebApp.java:161)
            at org.restframework.web.WebApp.init(WebApp.java:133)
            at org.restframework.web.WebApp.run(WebApp.java:83)
            at org.example.ExampleApp.main(ExampleApp.java:69)
        * */

        this.classDefinition.append("}");
        String newFilePath;

        File directory = new File(filePath+"\\"+dir);
        if (!directory.exists())
            directory.mkdir();

        if (dir.equals(NO_DIR))
            newFilePath = filePath.replace('.', File.separatorChar) + File.separator + name + ".java";
        else
            newFilePath = filePath.replace('.', File.separatorChar) + File.separator + dir + "\\" + name + ".java";

        File file = new File(newFilePath);
        Optional<FileRecord> fileRecord = with(file.getName());

        if (fileExists(file) && !(fileRecord.isPresent() && fileRecord.get().isUpdateAble())) {
            log.info("File [{}] already exists at: {}", name, newFilePath);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            if (fileRecord.isPresent() && fileRecord.get().isUpdateAble()) {
                this.classDefinition.insert(this.classDefinition.indexOf("@CompilationComponent"), "@UpdateComponent\n");
                log.info("Updated file [{}] at: {}", name, newFilePath);
            }
            else
                log.info("Created file [{}] at: {}", name, newFilePath);

            String javaCode = classDefinition.toString();
            writer.println(javaCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
