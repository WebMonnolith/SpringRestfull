package org.example;


import lombok.SneakyThrows;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.*;
import org.restframework.web.annotations.gen.GenDto;
import org.restframework.web.annotations.gen.GenModel;
import org.restframework.web.annotations.gen.GenProperties;
import org.restframework.web.annotations.gen.GenSpring;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.annotations.types.Model;
import org.restframework.web.core.generics.Generic;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRestConfiguration(useCustomGenerationStrategy = true)
@GenProperties(basePackage = "org.example.test")
@GenDto
@GenModel(
        tableName = "test_table",
        fields = {
                @FieldData(name="fname"),
                @FieldData(name="index")
        }
)
//@GenSpring
@SpringBootApplication
public class ExampleApp {
    @SneakyThrows
    public static void main(String[] args) {
        new WebApp(ExampleApp.class)
            .run(args);
    }

}


//@RestApi(
//    APIS= {
//        @API(
//            endpoint="/tools/api/v1/user",
//            model=@Model(generic = Generic.UUID,
//                        tableName = "test_user",
//                        apiName = "User",
//                        fields = {
//                                @FieldData(name = "fname"),
//                                @FieldData(name = "lname"),
//                                @FieldData(datatype = "double", name = "age"),
//                        }
//                ),
//            apiName="User",
//            basePackage = "org.example.test"
//        ),
//    }
//)