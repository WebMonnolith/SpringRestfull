package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;

@Template(
        templateName="Service",
        rule= SpringComponents.SERVICE,
        type=ClassTypes.CLASS
)
@Deprecated
public interface ServiceTemplate <ID, DTO, Model extends ModelFrame<ID>> extends TServiceCRUD<ID, DTO, Model> {
}
