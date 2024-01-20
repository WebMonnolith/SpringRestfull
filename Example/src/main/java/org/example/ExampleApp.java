package org.example;


import lombok.SneakyThrows;
import org.restframework.complex.sml.DecisionTree;
import org.restframework.web.annotations.*;
import org.restframework.web.core.builders.Modifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


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

        List<Double[]> dataSet = new ArrayList<>();

        Double[] data1 = { .23d, .34d, .67d, 0.1d };
        Double[] data2 = { .23d, .84d, .47d, 0.1d };
        Double[] data3 = { .21d, .64d, .97d, 0.1d };
        Double[] data4 = { .13d, .84d, .47d, 0.2d };
        Double[] data5 = { .13d, .88d, .99d, 0.2d };

        dataSet.add(data4);
        dataSet.add(data3);
        dataSet.add(data2);
        dataSet.add(data1);
        dataSet.add(data5);

        DecisionTree tree = new DecisionTree(dataSet, data1.length - 1);
        double result = tree.predict(data1);

        if (result == data1[3])
            System.out.println("Result " + result);

//        new WebApp(ExampleApp.class)
//        .run(args);
    }

}