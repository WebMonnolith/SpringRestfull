package org.restframework.web.core.templates;

import lombok.Getter;

@Getter
public enum ClassTypes {

    INTERFACE("public interface "),
    CLASS("public class "),
    ABSTRACT_CLASS("public abstract class "),
    FINAL_CLASS("public final class ");

    private final String value;

    ClassTypes(String value) {
        this.value = value;
    }
}
