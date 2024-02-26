package org.restframework.web.core.generics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericGeneration {
    private String generic;
    private String importStatement;
    private String generationType;
}
