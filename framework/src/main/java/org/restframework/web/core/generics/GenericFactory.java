package org.restframework.web.core.generics;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GenericFactory {

    public static final String NO_IMPORT = "";

    @Contract("_ -> new")
    public static @Nullable GenericGeneration create(@NotNull Generic generic) {
        switch (generic) {
            case LONG, INTEGER -> {
                return new GenericGeneration(generic.getValue(), NO_IMPORT, "AUTO");
            }
            case UUID -> {
                return new GenericGeneration(generic.getValue(), "import java.util.*", "UUID");
            }
        }

        return null;
    }
}
