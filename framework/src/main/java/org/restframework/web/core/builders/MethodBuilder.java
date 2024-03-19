package org.restframework.web.core.builders;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MethodBuilder implements Builder, BuilderUtils  {

    private final StringBuilder methodDefinition;

    public MethodBuilder(
            @NotNull String methodName,
            @NotNull String type,
            @NotNull Modifier access,
            @NotNull String @NotNull [] annotations)
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
                .append("() {\n")
                .append("\t\t return null;\n")
                .append("\t}\n");
    }

    public MethodBuilder(
            @NotNull String methodName,
            @NotNull String type,
            @NotNull Modifier access,
            @NotNull String args)
    {
        this.methodDefinition = new StringBuilder()
                .append("\t")
                .append(access.getValue())
                .append(" ")
                .append(type)
                .append(" ")
                .append(methodName)
                .append("(")
                .append(args)
                .append(") {\n")
                .append("\t\t return null\n")
                .append("\t}\n");
    }

    public MethodBuilder(
            @NotNull String methodName,
            @NotNull String type,
            @NotNull Modifier access,
            @NotNull String args,
            @NotNull String @NotNull [] annotations)
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
                .append("(")
                .append(args)
                .append(") {\n")
                .append("\t\t return null;\n")
                .append("\t}\n");
    }

    public MethodBuilder(
            @NotNull String methodName,
            @NotNull String type,
            @NotNull Modifier access,
            @NotNull String args,
            @NotNull String retValue,
            @NotNull String @NotNull [] annotations)
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
                .append("(")
                .append(args)
                .append(") {\n")
                .append("\t\t return ")
                .append(retValue)
                .append(";\n")
                .append("\t}\n");
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
