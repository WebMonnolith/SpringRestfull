package org.example;


import lombok.SneakyThrows;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.*;
import org.restframework.web.core.builders.Modifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@EnableRestConfiguration
@RestApi(
    APIS= {
        @API(
            endpoint="/tools/api/v1/user",
            model=@Model(generic = "UUID",
                        tableName = "test_user",
                        apiName = "User",
                        fields = {
                                @FieldData(access = Modifier.PRIVATE, datatype = "String", name = "fname"),
                                @FieldData(access = Modifier.PRIVATE, datatype = "String", name = "lname"),
                                @FieldData(access = Modifier.PRIVATE, datatype = "double", name = "age"),
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