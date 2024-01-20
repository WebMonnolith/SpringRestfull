package org.restframework.complex.sml;

import lombok.Data;

import java.util.function.Function;

/**
 * The TreeNode class represents a node in a decision tree used for making predictions.
 * Each node can either be an internal node with a question, or a leaf node with a prediction value.
 *
 * @see DecisionTree
 * @author Your Name
 * @version 1.0
 */
@Data
public class TreeNode {
    public Function<Double[], Boolean> question;
    private boolean isLeaf;
    private TreeNode left;
    private TreeNode right;
    private Double prediction;
}
