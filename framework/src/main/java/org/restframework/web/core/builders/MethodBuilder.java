package org.restframework.web.core.builders;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MethodBuilder implements Builder, BuilderUtils  {

    private final StringBuilder methodDefinition;

    public MethodBuilder(
            @NotNull String methodName,
            @NotNull String type,
            @NotNull Modifier access)
    {
        this.methodDefinition = new StringBuilder()
                .append("\t")
                .append(access.getValue())
                .append(" ")
                .append(type)
                .append(" ")
                .append(methodName)
                .append(";\n");
    }

    public MethodBuilder(
            @NotNull String methodName,
            @NotNull String type,
            @NotNull Modifier access,
            @NotNull String[] annotations)
    {
        this.methodDefinition = new StringBuilder();


        for (String annotation : annotations)
            this.addAnnotation(annotation);

        this.methodDefinition
                .append("\t")
                .append(access.getValue())
                .append(" ")
                .append(type)
                .append(" ")
                .append(methodName)
                .append(";\n");
    }

    @Override
    public <T> void addAnnotation(@NotNull T annotation) {
        this.methodDefinition.append("\t@").append(annotation).append("\n");
    }

    @Contract(pure = true)
    public @NotNull String getDefinition() {
        return this.methodDefinition.toString();
    }
}
