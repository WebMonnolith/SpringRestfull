package org.restframework.complex;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Data
@Slf4j
public class DecisionTree {

    private static final String RIGHT_CHILD = "RIGHT_CHILD";
    private static final String LEFT_CHILD = "LEFT_CHILD";
    private TreeNode root;

    public DecisionTree() {}
    public DecisionTree(@NotNull List<Double[]> data, Integer labelColumnIndex) {
        this.buildTree(data, labelColumnIndex);
        log.info("Decision tree has been build!");
    }

    public TreeNode buildTree(@NotNull List<Double[]> data, Integer labelColumnIndex) {
        if (data.isEmpty())
            return null;

        Double impurity = gini.apply(labelColumnIndex, data);

        if (impurity.compareTo(0.0d) == 0) {
            TreeNode node = new TreeNode();
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
        TreeNode node = new TreeNode();
        node.question = question;

        if (this.root == null) {
            this.root = node;
        }

        HashMap<String, List<Double[]>> partitionedData = partitionByQuestion(question, data);
        try {
            TreeNode left = this.buildTree(partitionedData.get(LEFT_CHILD), labelColumnIndex);
            TreeNode right = this.buildTree(partitionedData.get(RIGHT_CHILD), labelColumnIndex);

            node.setRight(right);
            node.setLeft(left);
        } catch (StackOverflowError e) {
            log.error("Stackoverflow Error: {} - (Could not match the input)", e.getMessage());
        }

        return node;

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
            questionGenerator = (index, value) -> (data) -> data[index].compareTo(value) <= 0;

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
    {
        HashMap<String, List<Double[]>> hashMap = new HashMap<>();

        hashMap.put(RIGHT_CHILD, new ArrayList<>());
        hashMap.put(LEFT_CHILD, new ArrayList<>());

        for (Double[] doubles : dataSet) {
            boolean value = partitionFunction.apply(doubles);

            List<Double[]> list;
            if (value) list = hashMap.get(RIGHT_CHILD);
            else list = hashMap.get(LEFT_CHILD);
            list.add(doubles);
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

    public double predict(Double[] data) {
        if (data != null && data.length > 0) {
            TreeNode node = this.root;
            try {
                while (!node.isLeaf()) {
                    boolean result = node.question.apply(data);
                    node = result ? node.getRight() : node.getLeft();
                }
                return node.getPrediction();
            }
            catch (NullPointerException e) {
                log.error("NullPointer error: {} - (The provided data is probably incorrect)", e.getMessage());
            }
        }

        return -1d;
    }
}
