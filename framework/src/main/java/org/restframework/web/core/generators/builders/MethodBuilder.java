package org.restframework.web.core.generators.builders;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.restframework.web.core.Modifier;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class MethodBuilder implements Builder<Method>, BuilderUtils  {
    @AllArgsConstructor
    @Data
    public static class Signature {

        private Modifier modifier;
        private String name;
        private Class<?> returnType;

    }

    private final StringBuilder methodDefinition;
    private final Signature signature;
    private Method method; // Store the generated Method object

    public MethodBuilder(Signature signature) {
        this.signature = signature;
        this.methodDefinition = new StringBuilder();
        methodDefinition
                .append(this.signature.modifier.toString().toLowerCase())
                .append(" ").append(this.signature.returnType.getSimpleName())
                .append(" ").append(this.signature.name)
                .append("() {}\n");
    }

    @Override
    public Method build() {
        methodDefinition.append("\t// Add your method logic here\n}\n");


        try {
            Class<?> dynamicClass = Class.forName("org.framework.web.core.GeneratedController");
            Class<?>[] paramTypes = {};
            return dynamicClass.getDeclaredMethod(this.signature.name, paramTypes);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return this.method;
    }

    public MethodBuilder addStatement(String s) {
        methodDefinition.append("\t").append(s).append("\n");
        return this;
    }

    public String get() {
        return this.methodDefinition.toString();
    }
}
