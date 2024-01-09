package org.restframework.web.core.helpers;

import org.restframework.web.annotations.FieldData;
import org.restframework.web.core.builders.Modifier;
import org.restframework.web.core.builders.FieldBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public class ModelHelper {
    public static @NotNull FieldData convertToField(Modifier access, String type, String name) {
        return new FieldData() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return FieldData.class;
            }

            @Override
            public Modifier access() {
                return access;
            }

            @Override
            public String datatype() {
                return type;
            }

            @Override
            public String name() {
                return name;
            }
        };
    }

    @Contract(pure = true)
    public static @NotNull FieldBuilder convertToFieldBuilder(@NotNull FieldData fieldData) {
        return new FieldBuilder(fieldData.name(), fieldData.datatype(), fieldData.access());
    }
}
