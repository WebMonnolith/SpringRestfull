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

        REQUEST_MAPPING("org.springframework.web.bind.annotation.RequestMapping"),
        REST_CONTROLLER("org.springframework.web.bind.annotation.RestController"),
        SERVICE("org.springframework.stereotype.Service"),
        REPOSITORY("org.springframework.stereotype.Repository"),
        SPRING_COMPONENT("org.springframework.stereotype.Component");

        private final String value;

        SpringAnnotations(String value) {
            this.value = value;
        }
    }

    @Getter
    enum LombokAnnotations {

        EQUALS_AND_HASHCODE("lombok.EqualsAndHashCode(callSuper=true)"),
        NO_ARGS_CONSTRUCTOR("lombok.NoArgsConstructor"),
        ALL_ARGS_CONSTRUCTOR("lombok.AllArgsConstructor"),
        GETTER("lombok.Getter"),
        SETTER("lombok.Setter"),
        DATA("lombok.Data"),
        BUILDER("lombok.Builder");

        private final String value;

        LombokAnnotations(String value) {
            this.value = value;
        }
    }

    @Getter
    enum PersistenceAnnotations {

        ENTITY("jakarta.persistence.Entity"),
        TABLE("jakarta.persistence.Table"),
        ID("jakarta.persistence.Id"),
        GENERATED_VALUE("jakarta.persistence.GeneratedValue"),
        GENERATION_TYPE("jakarta.persistence.GenerationType"),
        COLUMN("jakarta.persistence.Column");

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
