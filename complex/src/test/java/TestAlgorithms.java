import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.restframework.complex.algorithms.Searching.binSearch;
import static org.restframework.complex.algorithms.Sorting.*;

public class TestAlgorithms {

    @Test
    public void testBinarySearchRawWithSortedArray() {
        Integer[] integerArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int intKey = 6;
        int intIndex = binSearch(integerArray, intKey);
        printResult("Integer Array", intKey, intIndex);

        Double[] doubleArray = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        double doubleKey = 6.0;
        int doubleIndex = binSearch(doubleArray, doubleKey);
        printResult("Double Array", doubleKey, doubleIndex);
    }

    @Test
    public void testSortingAlgorithms() {
        // Sorting arrays
        Integer[] intArray = {4, 2, 8, 1, 6};
        Integer[] sortedIntArray = sort(intArray);
        System.out.println("Sorted int array: " + Arrays.toString(sortedIntArray));

        Double[] doubleArray = {3.5, 1.2, 7.8, 2.1, 6.6};
        Double[] sortedDoubleArray = sort(doubleArray);
        System.out.println("Sorted double array: " + Arrays.toString(sortedDoubleArray));

        // Sorting collections
        List<Integer> intList = new ArrayList<>(List.of(4, 2, 8, 1, 6));
        List<Integer> sortedIntList = sort(intList);
        System.out.println("Sorted int list: " + sortedIntList);

        List<Double> doubleList = new ArrayList<>(List.of(3.5, 1.2, 7.8, 2.1, 6.6));
        List<Double> sortedDoubleList = sort(doubleList);
        System.out.println("Sorted double list: " + sortedDoubleList);
    }

    private static void printResult(String arrayType, Object key, int index) {
        if (index >= 0) {
            System.out.println("Key " + key + " found in " + arrayType + " at index " + index);
        } else {
            System.out.println("Key " + key + " not found in " + arrayType);
        }
    }
}
