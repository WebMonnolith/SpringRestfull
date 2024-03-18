package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;

@Template(
        templateName="Controller",
        rule= SpringComponents.CONTROLLER,
        generics={"UUID"},
        type=ClassTypes.CLASS
)
@Deprecated
public interface ControllerTemplate <ID, DTO, Model extends ModelFrame<ID>> extends TControllerCRUD<ID, DTO, Model> {
}
