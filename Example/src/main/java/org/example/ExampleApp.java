package org.example;

import org.restframework.web.WebApp;
import org.restframework.web.annotations.*;
import org.restframework.web.core.generators.builders.Modifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRestConfiguration
@RestApi(
    APIS= {
        @API(
            endpoint="/tools/api/testing1",
            model=@Model(generic = "UUID",
                        tableName = "test_table1",
                        apiName = "Test1",
                        fields = {
                                @FieldData(access = Modifier.PRIVATE, datatype = "int", name = "testField"),
                                @FieldData(access = Modifier.PRIVATE, datatype = "double", name = "testField2"),
                        }
                ),
            apiName="Test1",
            basePackage = "org.example.test.test1"
        ),
        @API(
            endpoint="/tools/api/testing2",
            model=@Model(generic = "UUID",
                        tableName = "test_table2",
                        apiName = "Test2",
                        fields = {
                                @FieldData(access = Modifier.PRIVATE, datatype = "int", name = "testField"),
                                @FieldData(access = Modifier.PRIVATE, datatype = "double", name = "testField2"),
                        }
                    ),
            apiName="Test2",
            basePackage = "org.example.test.test2"
        )
    }
)
@SpringBootApplication
public class ExampleApp {
    public static void main(String[] args) {
        WebApp app = new WebApp(ExampleApp.class);
        app.run(ExampleApp.class, args);
    }

}