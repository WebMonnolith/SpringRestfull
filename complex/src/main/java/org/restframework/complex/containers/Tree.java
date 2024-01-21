package org.restframework.complex.containers;

import org.jetbrains.annotations.NotNull;
import org.restframework.complex.containers.sml.DecisionTree;
import org.restframework.complex.containers.sml.DecisionTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple tree utility class implementation with basic operations.
 *
 * @author  Jessy van Polanen
 * @see     BinTreeNode
 * @see     Node
 * @see     org.restframework.complex.containers.sml.DecisionTreeNode
 * @version 1.0
 */
public class Tree {
    /**
     * Converts a binary tree into a List<T> using inorder traversal.
     *
     * @param tree The binary tree to be converted.
     * @return A List<T> containing the elements of the binary tree in inorder.
     */
    public static <T> @NotNull List<T> convertToList(@NotNull BinaryTree<T> tree) {
        List<T> result = new ArrayList<>();
        Tree.inorderTraversal(tree.getRoot(), result);
        return result;
    }

    /**
     * Converts a decision tree into a List<Double> using inorder traversal.
     *
     * @param tree The decision tree to be converted.
     * @return A List<Double> containing the elements of the decision tree in inorder.
     */
    public static @NotNull List<Double> convertToList(@NotNull DecisionTree tree) {
        List<Double> result = new ArrayList<>();
        Tree.inorderTraversal(tree.getRoot(), result);
        return result;
    }

    private static <T> void inorderTraversal(BinTreeNode<T> node, List<T> result) {
        if (node == null)
            return;

        Tree.inorderTraversal(node.getLeft(), result);
        result.add(node.getKey());
        Tree.inorderTraversal(node.getRight(), result);
    }

    private static void inorderTraversal(DecisionTreeNode node, List<Double> result) {
        if (node == null)
            return;

        Tree.inorderTraversal(node.getLeft(), result);
        result.add(node.getPrediction());
        Tree.inorderTraversal(node.getRight(), result);
    }
}
