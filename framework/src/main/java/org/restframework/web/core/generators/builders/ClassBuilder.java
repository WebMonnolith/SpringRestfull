package org.restframework.web.core.generators.builders;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.templates.ClassTypes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import static org.restframework.web.core.FileHelper.NO_DIR;

@Slf4j
public final class ClassBuilder implements Builder<Class<?>>, BuilderUtils {

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
                .append("import org.restframework.web.core.templates.*;\n\n")
                .append("import java.util.*;\n\n");

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
    public void addMethod(String method) {
        this.classDefinition.append(method);
    }

    @Override
    public void addMethod(Method method) {
        classDefinition.append(method.getReturnType().getSimpleName()).append(" ")
                .append(method.getName()).append("() {\n")
                .append("\treturn null;\n}\n");
    }

    @Override
    public void addField(FieldBuilder builder) {
        this.classDefinition.append(builder.getDefinition());
    }

    @Override
    public Class<?> build(@NotNull String filePath, @NotNull String dir) {
        this.classDefinition.append("}");
        boolean newDirFlag = false;
        String newFilePath = filePath;

        String javaCode = classDefinition.toString();

        File directory = new File(filePath+"\\"+dir);
        if (!directory.exists())
            newDirFlag = directory.mkdir();

        if (dir.equals(NO_DIR))
            newFilePath = filePath.replace('.', File.separatorChar) + File.separator + name + ".java";
        else
            newFilePath = filePath.replace('.', File.separatorChar) + File.separator + dir + "\\" + name + ".java";

        log.info("FILEPATH: {}", newFilePath);
        File file = new File(newFilePath);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(javaCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
