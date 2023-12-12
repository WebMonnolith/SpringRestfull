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
            "/tools/calculation_api/adiabatic",
            "/tools/calculation_api/AHU"
    },
    models={
            @Model(generic="UUID",
                    tableName="t_cooling",
                    apiName="Cooling",
                    fields={
                            @FieldData(access=Modifier.PRIVATE, datatype="int", name="testField"),
                            @FieldData(access=Modifier.PRIVATE, datatype="double", name="testField2"),
                    }
            ),
            @Model(generic="UUID",
                    tableName="t_adiabatic",
                    apiName="Adiabatic"
            ),
            @Model(generic="UUID",
                    tableName="t_AHU",
                    apiName="AHU"
            ),
    },
    templates={
            ControllerTemplate.class,
            ServiceTemplate.class,
            RepoTemplate.class,
    },
    apiNames={
            "Cooling",
            "Adiabatic",
            "AHU"
    },
    basePackage="org.example"
)
@SpringBootApplication
public class ExampleApp {
    public static void main(String[] args) {
        WebApp app = new WebApp(ExampleApp.class);
        app.run(ExampleApp.class, args);
    }
}