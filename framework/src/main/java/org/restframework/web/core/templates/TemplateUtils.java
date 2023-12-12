package org.restframework.web.core.templates;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.Template;

@SuppressWarnings("unused")
public class TemplateUtils {
    public static boolean useImplementation(@NotNull Template template) {
        if (template.rule() == SpringComponents.NONE)
            return false;

        switch (template.rule()) {
            case REPO -> {
                return false;
            }
            case SERVICE, CONTROLLER -> {
                return true;
            }
        }

        return false;
    }

    public static boolean useInheritance(@NotNull Template template) {
        if (template.rule() == SpringComponents.NONE)
            return false;

        switch (template.rule()) {
            case REPO -> {
                return true;
            }
            case SERVICE, CONTROLLER -> {
                return false;
            }
        }

        return false;
    }

    public static boolean hasGenerics(@NotNull Template template) {
        return template.generics().length == 0;
    }
}
