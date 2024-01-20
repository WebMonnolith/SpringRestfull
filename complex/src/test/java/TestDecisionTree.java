import org.junit.jupiter.api.Test;
import org.restframework.complex.sml.DecisionTree;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDecisionTree {
    @Test
    public void createA_DecisionTreeWithSetGeneratedInputAndLabels() {
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

        assertEquals(result, data1[3]);
        System.out.println("Result " + result);
    }
}
