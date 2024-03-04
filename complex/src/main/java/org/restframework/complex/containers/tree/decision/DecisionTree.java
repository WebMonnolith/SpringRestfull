package org.restframework.complex.containers.tree.decision;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The DecisionTree class represents a decision tree for making predictions based on input data.
 * It uses a provided dataset to build the tree and can make predictions for new data points.
 * <pre>
 *
 * List<Double[]> dataSet = new ArrayList<>();
 * // ... (add data points to the dataset)
 *
 * DecisionTree tree = new DecisionTree(dataSet, numFeatures);
 * double result = tree.predict(newDataPoint);
 * if (result == expectedValue)
 *     System.out.println("Prediction is correct: " + result);
 *
 * </pre>
 * @author  Jessy van Polanen
 * @see     DecisionTreeNode
 * @version 1.0
 */
@Data
@Slf4j
public class DecisionTree {

    private static final String RIGHT_CHILD = "RIGHT_CHILD";
    private static final String LEFT_CHILD = "LEFT_CHILD";
    private DecisionTreeNode root;
    private boolean noErrors = true;

    /**
     * Constructs a DecisionTree instance with the given dataset and number of features.

     */
    public DecisionTree() {}

    /**
     * Constructs a DecisionTree instance with the given dataset and number of features.
     *
     * @param data              The dataset used for building the decision tree.
     * @param labelColumnIndex  The index of tree insertion.
     */
    public DecisionTree(@NotNull List<Double[]> data, Integer labelColumnIndex) {
        this.buildTree(data, labelColumnIndex);
        if (this.noErrors) log.info("Decision tree has been build!");
    }

    /**
     * Builds a DecisionTree behind the scene with the given dataset and number of features.
     *
     * @param data              The dataset used for building the decision tree.
     * @param labelColumnIndex  The index of tree insertion.
     * @return                  A node to the corresponding Tree structure
     */
    public DecisionTreeNode buildTree(@NotNull List<Double[]> data, Integer labelColumnIndex) {
        if (data.isEmpty())
            return null;

        Double impurity = gini.apply(labelColumnIndex, data);

        if (impurity.compareTo(0.0d) == 0) {
            DecisionTreeNode node = new DecisionTreeNode();
            node.setLeaf(true);
            node.setPrediction(data.get(0)[labelColumnIndex]);
            return node;
        }

        // TODO Leaf node concept and how to use question while classifying
        Double bestInformationGain = 0.0d;
        int finalIndex = -1;
        Double finalValue = Double.MIN_VALUE;

        int numberOfFeatures = labelColumnIndex;
        for (int i = 0; i < numberOfFeatures; i++) {
            HashSet<Double> uniqueDataPoints = getUniqueDataPoints(data, i);
            for (Double value : uniqueDataPoints) {
                Double tempInformationGain =
                    informationGain(
                        impurity,
                        questionGenerator,
                        data,
                        labelColumnIndex,
                        value
                    );

                if (bestInformationGain.compareTo(tempInformationGain) < 0) {
                    bestInformationGain = tempInformationGain;
                    finalIndex = i;
                    finalValue = value;
                }
            }

        }

        Function<Double[], Boolean> question = questionGenerator.apply(finalIndex, finalValue);
        DecisionTreeNode node = new DecisionTreeNode();
        node.question = question;

        if (this.root == null) {
            this.root = node;
        }

        HashMap<String, List<Double[]>> partitionedData = partitionByQuestion(question, data);

        try {
            DecisionTreeNode left = this.buildTree(partitionedData.get(LEFT_CHILD), labelColumnIndex);
            DecisionTreeNode right = this.buildTree(partitionedData.get(RIGHT_CHILD), labelColumnIndex);

            node.setRight(right);
            node.setLeft(left);
        } catch (StackOverflowError e) {
            this.noErrors = false;
            log.error("Stackoverflow Error: {} - (Could not match the input)", e.getMessage());
        }

        return node;

    }

    /**
     * Predicts the outcome for a given data point using the constructed decision tree.
     *
     * @param data The data point for which the prediction is made.
     * @return The predicted outcome for the given data point, -1 = InvalidOutput.
     */
    public double predict(Double[] data) {
        if (data != null && data.length > 0) {
            DecisionTreeNode node = this.root;
            try {
                while (!node.isLeaf()) {
                    boolean result = node.question.apply(data);
                    node = result ? node.getRight() : node.getLeft();
                }
                return node.getPrediction();
            }
            catch (NullPointerException e) {
                this.noErrors = false;
                log.error("NullPointer error: {} - (The provided data is probably incorrect)", e.getMessage());
            }
        }

        return -1d;
    }

    private final BiFunction<Integer, List<Double[]>, Double> gini = (index, data) -> {
        if (data.isEmpty())
            return 0.0d;

        Double impurity = 1.0d;
        HashMap<Double, Integer> frequencyMap = findFreqMap(data, index);

        for (Double key : frequencyMap.keySet()) {
            double probability = (double) frequencyMap.get(key) / (double) data.size();
            impurity = impurity - Math.pow(probability, 2);
        }

        return impurity;

    };

    // TODO handling of categorical column needs to added
    private final BiFunction<Integer, Double, Function<Double[], Boolean>>
        questionGenerator = (index, value) -> {
            return (data) -> {
                try {
                    return data[index].compareTo(value) <= 0;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    this.noErrors = false;
                    log.error("IndexOutOfBounds error: {} - (The label indexes are probably incorrect)", e.getMessage());
                }
                return null;
            };
        };

    private @NotNull HashMap<Double, Integer> findFreqMap(
            @NotNull List<Double[]> data,
            Integer index)
    {
        HashMap<Double, Integer> freqMap = new HashMap<>();

        for (Double[] datum : data) {
            Double point = datum[index];
            if (freqMap.containsKey(point)) {
                int value = freqMap.get(point);
                freqMap.put(point, value + 1);
            } else {
                freqMap.put(point, 1);
            }
        }

        return freqMap;
    }

    private @NotNull HashMap<String, List<Double[]>> partitionByQuestion(
            Function<Double[], Boolean> partitionFunction,
            @NotNull List<Double[]> dataSet)
            throws ArrayIndexOutOfBoundsException
    {
        HashMap<String, List<Double[]>> hashMap = new HashMap<>();

        hashMap.put(RIGHT_CHILD, new ArrayList<>());
        hashMap.put(LEFT_CHILD, new ArrayList<>());

        try {
            for (Double[] doubles : dataSet) {
                boolean value = partitionFunction.apply(doubles);
                List<Double[]> list;
                if (value) list = hashMap.get(RIGHT_CHILD);
                else list = hashMap.get(LEFT_CHILD);

                list.add(doubles);

            }
        }
        catch (NullPointerException e) {
            this.noErrors = false;
            log.error("NullPointer error: {} - (The label indexes are probably incorrect, or the other inputs)", e.getMessage());
        }

        return hashMap;
    }

    private @NotNull Double informationGain(
            Double currentImpurity,
            @NotNull BiFunction<Integer,
            Double, Function<Double[],
            Boolean>> question,
            List<Double[]> data, Integer index,
            Double value)
    {
        Function<Double[], Boolean> partitonFunction = question.apply(index, value);
        HashMap<String, List<Double[]>> partitionData = partitionByQuestion(partitonFunction, data);

        // Edge case if size is zero
        double leftProbability = !partitionData.get(LEFT_CHILD).isEmpty()
                ? ((double) partitionData.get(LEFT_CHILD).size() / (double) data.size())
                : 0.0d;

        double leftGini = gini.apply(index, partitionData.get(LEFT_CHILD));
        double rightProbability = 1 - leftProbability;
        double rightGini = gini.apply(index, partitionData.get(RIGHT_CHILD));
        currentImpurity = currentImpurity - (leftProbability * leftGini) - (rightGini * rightProbability);
        return currentImpurity;
    }

    private @NotNull HashSet<Double> getUniqueDataPoints(@NotNull List<Double[]> data, int index) {
        HashSet<Double> uniqueDataPoints = new HashSet<>();
        data.forEach((n) -> uniqueDataPoints.add(n[index]));
        return uniqueDataPoints;
    }
}
