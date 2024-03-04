package org.example;


import lombok.SneakyThrows;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.*;
import org.restframework.web.core.builders.Modifier;
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
                        }
                ),
            apiName="Test1",
            basePackage = "org.example.test"
        ),
//        @API(
//            endpoint="/tools/api/testing2",
//            model=@Model(generic = "UUID",
//                        tableName = "test_table2",
//                        apiName = "Test2",
//                        fields = {
//                                @FieldData(access = Modifier.PRIVATE, datatype = "int", name = "testField"),
//                                @FieldData(access = Modifier.PRIVATE, datatype = "double", name = "testField2"),
//                        }
//                    ),
//            apiName="Test2",
//            basePackage = "org.example.test"
//        )
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