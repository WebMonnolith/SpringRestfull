package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.builders.MethodBuilder;
import org.restframework.web.core.builders.Modifier;

public class ControllerCompilationStrategy implements CompilationStrategy {

    @Override
    public void execute(@NotNull CompilationContext context) {
        String serviceID = context.getApi().apiName().toLowerCase() + "Service";

        context.getBuilder().addField(
                new FieldBuilder(
                        serviceID,
                        context.getApi().apiName() + "Service",
                        Modifier.PRIVATE_FINAL));

        if (context.isDefaultTemplateMethodImpl()) {
            context.getBuilder().addMethod(new MethodBuilder(
                    "insertEntity",
                    "int",
                    Modifier.PUBLIC,
                    context.getDtoName() + " " + context.getDtoName().toLowerCase(),
                    serviceID+".insert("+context.getDtoName().toLowerCase()+")",
                    new String[]{"Override", "PostMapping", "ResponseStatus(HttpStatus.ACCEPTED)"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "getAllEntities",
                    "List<"+context.getDtoName()+">",
                    Modifier.PUBLIC, "",
                    serviceID+".getAll()",
                    new String[]{"Override", "GetMapping", "ResponseStatus(HttpStatus.OK)"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "removeEntityById",
                    "boolean",
                    Modifier.PUBLIC,
                    context.getGeneric() + " id",
                    serviceID+".removeById(id)",
                    new String[]{"Override", "DeleteMapping", "ResponseStatus(HttpStatus.FOUND)"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "updateEntity",
                    "boolean",
                    Modifier.PUBLIC,
                    context.getGeneric() + " id, " + context.getModelName() + " " + context.getModelName().toLowerCase(),
                    serviceID+".update(id, "+context.getModelName().toLowerCase()+")",
                    new String[]{"Override", "PutMapping", "ResponseStatus(HttpStatus.OK)"}
            ));

        }
    }
}
