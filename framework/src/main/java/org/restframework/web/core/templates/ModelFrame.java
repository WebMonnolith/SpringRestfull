package org.restframework.web.core.templates;

//import org.framework.web.annotations.Template;
//import org.framework.web.core.templates.TemplateRules;
//
//@Template(
//        templateName="Model",
//        rule=TemplateRules.MODEL,
//        generics={"UUID"}
//)
@SuppressWarnings("unused")
public abstract class ModelFrame <ID> {
    public abstract ID getId();
    public abstract void setId(ID id);
}
