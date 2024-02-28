package org.example;


import lombok.SneakyThrows;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.*;
import org.restframework.web.core.generics.Generic;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRestConfiguration
@RestApi(
    APIS= {
        @API(
            endpoint="/tools/api/v1/user",
            model=@Model(generic = Generic.UUID,
                        tableName = "test_user",
                        apiName = "User",
                        fields = {
                                @FieldData(name = "fname"),
                                @FieldData(name = "lname"),
                                @FieldData(datatype = "double", name = "age"),
                        }
                ),
            apiName="User",
            basePackage = "org.example.test"
        ),
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