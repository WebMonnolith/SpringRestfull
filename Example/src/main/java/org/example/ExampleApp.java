package org.example;

import org.restframework.web.WebApp;
import org.restframework.web.annotations.FieldData;
import org.restframework.web.annotations.Model;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.core.Modifier;
import org.restframework.web.core.templates.ControllerTemplate;
import org.restframework.web.core.templates.RepoTemplate;
import org.restframework.web.core.templates.ServiceTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@RestApi(
    endpoints={
            "/tools/calculation_api/cooling",
    },
    models={
            @Model(generic="UUID",
                    tableName="t_cooling",
                    apiName="Cooling",
                    fields={
                            @FieldData(access=Modifier.PRIVATE, datatype="int", name="testField"),
                            @FieldData(access=Modifier.PRIVATE, datatype="double", name="testField2"),
                    }
            )
    },
    templates={
            ControllerTemplate.class,
            ServiceTemplate.class,
            RepoTemplate.class,
    },
    apiNames={
            "Cooling",
    },
    basePackage="org.example.test"
)
@SpringBootApplication
public class ExampleApp {
    public static void main(String[] args) {
        WebApp app = new WebApp(ExampleApp.class);
        app.run(ExampleApp.class, args);
    }
}