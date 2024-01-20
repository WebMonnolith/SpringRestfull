package org.restframework.complex.containers.sml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.restframework.complex.containers.Node;

import java.util.function.Function;

/**
 * The TreeNode class represents a node in a decision tree used for making predictions.
 * Each node can either be an internal node with a question, or a leaf node with a prediction value.
 *
 * @see DecisionTree
 * @author Your Name
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecisionTreeNode extends Node<DecisionTreeNode> {
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
     * The prediction value for leaf nodes. Null for internal nodes.
     */
    private Double prediction;
}
