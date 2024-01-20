package org.restframework.complex.sml;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TreeNode {
    /**
     * The question associated with an internal node.
     * If null, the node is a leaf node.
     */
    public Function<Double[], Boolean> question;

    /**
     * Flag indicating whether the node is a leaf node.
     */
    private boolean isLeaf;

    /**
     * The left subtree of the node. Null for leaf nodes.
     */
    private TreeNode left;

    /**
     * The right subtree of the node. Null for leaf nodes.
     */
    private TreeNode right;

    /**
     * The prediction value for leaf nodes. Null for internal nodes.
     */
    private Double prediction;
}
