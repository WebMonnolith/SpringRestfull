package org.example;


import lombok.SneakyThrows;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.EnableRestConfiguration;
import org.restframework.web.annotations.gen.GenDto;
import org.restframework.web.annotations.gen.GenModel;
import org.restframework.web.annotations.gen.GenProperties;
import org.restframework.web.annotations.gen.GenSpring;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.core.generics.Generic;
import org.restframework.web.core.templates.TControllerResponseEntity;
import org.restframework.web.core.templates.TServiceResponseEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRestConfiguration(useCustomGenerationStrategy = true)
@GenProperties(basePackage = "org.example.test", indexColumnType = Generic.LONG)
@GenDto
@GenModel(
        tableName = "test_table",
        fields = {
                @FieldData(name="fname"),
                @FieldData(name="index")
        }
)
@GenSpring(service = TServiceResponseEntity.class, controller = TControllerResponseEntity.class)
@SpringBootApplication
public class ExampleApp {
    @SneakyThrows
    public static void main(String[] args) {
        new WebApp(ExampleApp.class)
            .run(args);
    }

}