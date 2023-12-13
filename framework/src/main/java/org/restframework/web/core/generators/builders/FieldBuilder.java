package org.restframework.web.core.generators.builders;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class FieldBuilder implements Builder, BuilderUtils {
    private final StringBuilder fieldDefinition;

    public FieldBuilder(
            @NotNull String fieldName,
            @NotNull String type,
            @NotNull Modifier access)
    {
        this.fieldDefinition = new StringBuilder()
                .append("\t")
                .append(access.getValue())
                .append(" ")
                .append(type)
                .append(" ")
                .append(fieldName)
                .append(";\n");
    }

    public FieldBuilder(
            @NotNull String fieldName,
            @NotNull String type,
            @NotNull Modifier access,
            @NotNull String[] annotations)
    {
        this.fieldDefinition = new StringBuilder();


        for (String annotation : annotations)
            this.addAnnotation(annotation);

        this.fieldDefinition
                .append("\t")
                .append(access.getValue())
                .append(" ")
                .append(type)
                .append(" ")
                .append(fieldName)
                .append(";\n");
    }

    @Contract(pure = true)
    public @NotNull String getDefinition() {
        return this.fieldDefinition.toString();
    }

    @Override
    public <T> void addAnnotation(@NotNull T annotation) {
        this.fieldDefinition.append("\t@").append(annotation).append("\n");
    }

    public FieldBuilder addStatement(@NotNull String statement) {
        this.fieldDefinition.append(statement);
        return this;
    }
}
