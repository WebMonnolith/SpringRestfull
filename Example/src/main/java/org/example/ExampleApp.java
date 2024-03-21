package org.example;


import lombok.SneakyThrows;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.EnableRestConfiguration;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.annotations.gen.GenDto;
import org.restframework.web.annotations.gen.GenModel;
import org.restframework.web.annotations.gen.GenProperties;
import org.restframework.web.annotations.gen.GenSpring;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.annotations.types.Model;
import org.restframework.web.core.generics.Generic;
import org.restframework.web.core.templates.TControllerEntityResponse;
import org.restframework.web.core.templates.TControllerEntityResponseWildcard;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableRestConfiguration(useCustomGenerationStrategy = true)
//@GenProperties(basePackage = "org.example", indexColumnType = Generic.INTEGER, apiName = "File")
//@GenDto(abbrev = "Request")
//@GenModel(
//        abbrev = "Response",
//        tableName = "test_table",
//        fields = {
//                @FieldData(name="fname"),
//                @FieldData(name="index"),
//                @FieldData(name="age")
//        }
//)
//@GenSpring(controller= TControllerEntityResponse.class)
@EnableRestConfiguration
@RestApi(
        controller = TControllerEntityResponseWildcard.class,
        basePackage = "org.example",
        APIS = {
                @API(
                        apiPackage = "api",
                        endpoint = "/api/v1/test",
                        apiName = "Api",
                        model = @Model(
                                generic = Generic.UUID,
                                tableName ="test_table",
                                fields = {
                                        @FieldData(name="fname"),
                                        @FieldData(name="index"),
                                        @FieldData(name="age")
                                }
                        )
                ),
                @API(
                        apiPackage = "example_test",
                        endpoint = "/api/v1/example",
                        apiName = "Example",
                        model = @Model(
                                generic = Generic.INTEGER,
                                tableName ="test_example",
                                fields = {
                                        @FieldData(datatype = "int", name="index"),
                                }
                        )
                )
        }
)
@SpringBootApplication
public class ExampleApp {
    @SneakyThrows
    public static void main(String[] args) {
        new WebApp(ExampleApp.class)
            .run(args);
    }

}