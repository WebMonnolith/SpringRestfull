package org.example;

import org.restframework.web.WebApp;
import org.restframework.web.annotations.FieldData;
import org.restframework.web.annotations.Model;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.core.generators.builders.Modifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@RestApi(
        endpoints={
                "/tools/api/testing",
        },
        models={
                @Model(generic="UUID",
                        tableName="test_table",
                        apiName="Test",
                        fields={
                                @FieldData(access=Modifier.PRIVATE, datatype="int", name="testField"),
                                @FieldData(access=Modifier.PRIVATE, datatype="double", name="testField2"),
                        }
                )
        },
        apiNames={
                "Test",
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