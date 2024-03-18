package org.restframework.web.core.generators.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.types.Model;
import org.restframework.web.annotations.Template;
import org.restframework.web.core.builders.ClassBuilder;

@Data
@AllArgsConstructor
@Builder
public class CompilationContext {
    private API api;
    private ClassBuilder builder;
    private Class<?> template;
    private Template templateAnnotation;
    private Model modelAnnotation;
    private String modelName;
    private String dtoName;
    private String generic;
    private MethodImplementations methods;
}
