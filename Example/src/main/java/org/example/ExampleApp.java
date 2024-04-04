package org.example;


import lombok.SneakyThrows;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.EnableRestConfiguration;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.annotations.gen.*;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.annotations.types.Model;
import org.restframework.web.core.generics.Generic;
import org.restframework.web.core.templates.TControllerEntityResponse;
import org.restframework.web.core.templates.TControllerEntityResponseWildcard;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRestConfiguration(useCustomGenerationStrategy = true)
@GenProperties(basePackage = "org.example", indexColumnType = Generic.INTEGER, apiName = "File")
@GenDto(abbrev = "Request")
@GenModel(
        abbrev = "Response",
        tableName = "test_table",
        fields = {
                @FieldData(name="fname"),
                @FieldData(name="index"),
                @FieldData(name="age")
        }
)
@GenSpring(controller= TControllerEntityResponse.class)
@GenComponent(name="Test1", packageName = "components")
@GenComponent(name="Test2", packageName = "components")
@SpringBootApplication
public class ExampleApp {
    @SneakyThrows
    public static void main(String[] args) {
        new WebApp(ExampleApp.class)
            .run(args);
    }

}