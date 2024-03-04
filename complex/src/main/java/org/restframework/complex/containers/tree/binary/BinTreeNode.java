package org.restframework.complex.containers.tree.binary;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.restframework.complex.containers.tree.Node;

/**
 * A node in a binary tree, extending the generic Node class.
 *
 * @param <T> The type of elements stored in the node.
 *
 * @author  Jessy van Polanen
 * @see     BinaryTree
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BinTreeNode<T> extends Node<BinTreeNode<T>> {

    /**
     * The key associated with the node.
     */
    private T key;

    /**
     * Constructs a BinTreeNode with a specified item as the key.
     *
     * @param item The item to be set as the key of the node.
     */
    public BinTreeNode(T item) {
        this.key = item;
        this.left = this.right = null;
    }

    /**
     * Sets the left child of the node with the specified item as the key.
     *
     * @param item The item to be set as the key of the left child.
     */
    public void setLeftData(T item) {
        this.left = new BinTreeNode<>(item);
    }

    /**
     * Sets the right child of the node with the specified item as the key.
     *
     * @param item The item to be set as the key of the right child.
     */
    public void setRightData(T item) {
        this.right = new BinTreeNode<>(item);
    }

}
