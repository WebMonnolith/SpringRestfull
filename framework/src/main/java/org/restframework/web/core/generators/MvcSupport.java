package org.restframework.web.core.generators;

import lombok.Getter;
import org.restframework.web.core.templates.SpringComponents;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface MvcSupport {

    @Getter
    enum SpringAnnotations {

        REQUEST_MAPPING("RequestMapping"),
        REST_CONTROLLER("RestController"),
        SERVICE("Service"),
        REPOSITORY("Repository"),
        SPRING_COMPONENT("Component");

        private final String value;

        SpringAnnotations(String value) {
            this.value = value;
        }
    }

    @Getter
    enum LombokAnnotations {

        EQUALS_AND_HASHCODE("EqualsAndHashCode(callSuper=true)"),
        NO_ARGS_CONSTRUCTOR("NoArgsConstructor"),
        ALL_ARGS_CONSTRUCTOR("AllArgsConstructor"),
        GETTER("Getter"),
        SETTER("Setter"),
        DATA("Data"),
        BUILDER("Builder");

        private final String value;

        LombokAnnotations(String value) {
            this.value = value;
        }
    }

    @Getter
    enum PersistenceAnnotations {

        ENTITY("Entity"),
        TABLE("Table"),
        ID("Id"),
        GENERATED_VALUE("GeneratedValue"),
        GENERATION_TYPE("GenerationType"),
        COLUMN("Column");

        private final String value;

        PersistenceAnnotations(String value) {
            this.value = value;
        }
    }

    List<String> ruleHolder = new ArrayList<>();
    String NO_VALUE = "";

    void call(SpringComponents rules, String value);

    default @NotNull String[] getAllRules(@NotNull List<String> rules) {
        return rules.toArray(new String[0]);
    }

    @Contract(pure = true)
    default @NotNull String makeRequestMapping(@NotNull String endpoint) {
        return String.format("%s(\"%s\")", SpringAnnotations.REQUEST_MAPPING.getValue(), endpoint);
    }

    @Contract(pure = true)
    default @NotNull String makeGenerationType(@NotNull String id) {
        return String.format("%s(strategy=%s.%s)",
                PersistenceAnnotations.GENERATED_VALUE.getValue(),
                PersistenceAnnotations.GENERATION_TYPE.getValue(),
                id);
    }

    @Contract(pure = true)
    default @NotNull String makeTable(@NotNull String name) {
        return String.format("%s(name=\"%s\")", PersistenceAnnotations.TABLE.getValue(), name);
    }

    default @NotNull String[] callInner(SpringComponents rules, String value) {
        if (!ruleHolder.isEmpty())
            ruleHolder.clear();

        this.call(rules, value);

        return getAllRules(ruleHolder);
    }
}
