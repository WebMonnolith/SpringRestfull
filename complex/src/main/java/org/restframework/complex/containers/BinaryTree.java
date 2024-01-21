package org.restframework.complex.containers;

import lombok.*;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple binary tree implementation with basic operations. Will be expanded in the future.
 * <pre>
 *         BinaryTree<Integer> tree = new BinaryTree<>(1);
 *          ...
 *         tree.getRoot().setLeft(new BinTreeNode<>(2));
 *         tree.getRoot().setRight(new BinTreeNode<>(3));
 *         tree.getRoot().getLeft().setLeft(new BinTreeNode<>(4));
 *         tree.getRoot().getLeft().setRight(new BinTreeNode<>(5));
 *         ...
 *         System.out.print("Inorder traversal before insertion:");
 *         tree.inorder(tree.getRoot());
 *          ...
 *         tree.insert(tree.getRoot(), 12);
 *          ...
 *         System.out.print("Inorder traversal after insertion:");
 *         tree.inorder(tree.getRoot());
 * </pre>
 *
 * @param <T> The type of elements stored in the binary tree.
 *
 * @author  Jessy van Polanen
 * @see     BinTreeNode
 * @version 1.0
 */
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class BinaryTree <T> {

    /**
     * The root node of the binary tree.
     */
    @Getter
    @Setter
    private BinTreeNode<T> root;


    /**
     * Constructs a BinaryTree with a specified key as the root.
     *
     * @param key The key to be set as the root of the binary tree.
     */
    public BinaryTree(T key) {
        this.root = new BinTreeNode<>(key);
    }

    /**
     * Performs an inorder traversal starting from the given node.
     *
     * @param temp The starting node for the inorder traversal.
     */
    public void inorder(BinTreeNode<T> temp) {
        if (temp == null)
            return;

        this.inorder(temp.getLeft());
        System.out.print(temp.getKey() + " ");
        this.inorder(temp.getRight());
    }

    /**
     * Inserts a new key into the binary tree.
     *
     * @param temp The root node of the subtree where the key should be inserted.
     * @param key  The key to be inserted into the binary tree.
     */
    public void insert(BinTreeNode<T> temp, T key) {
        if (temp == null) {
            this.root = new BinTreeNode<>(key);
            return;
        }

        Queue<BinTreeNode<T>> q = new LinkedList<>();
        q.add(temp);

        while (!q.isEmpty()) {
            temp = q.peek();
            q.remove();
            if (temp.getLeft() == null) {
                temp.setLeft(new BinTreeNode<>(key));
                break;
            } else
                q.add(temp.getLeft());
            if (temp.getRight() == null) {
                temp.setRight(new BinTreeNode<>(key));
                break;
            } else
                q.add(temp.getRight());
        }
    }

    /**
     * Deletes the deepest node in the binary tree.
     *
     * @param delNode The node to be deleted.
     */
    public void deleteDeepest(BinTreeNode<T> delNode) {
        Queue<BinTreeNode<T>> q = new LinkedList<>();
        q.add(this.root);

        BinTreeNode<T> temp;

        while (!q.isEmpty()) {
            temp = q.peek();
            q.remove();

            if (temp == delNode)
                return;
            if (temp.getRight() != null) {
                if (temp.getRight() == delNode) {
                    temp.setRight(null);
                    return;
                } else
                    q.add(temp.getRight());
            }

            if (temp.getLeft() != null) {
                if (temp.getLeft() == delNode) {
                    temp.setLeft(null);
                    return;
                } else
                    q.add(temp.getLeft());
            }
        }
    }

    /**
     * Deletes a key from the binary tree.
     *
     * @param key The key to be deleted from the binary tree.
     */
    public void delete(T key) {
        if (this.root == null)
            return;

        if (this.root.getLeft() == null && this.root.getRight() == null) {
            if (this.root.getKey() == key)
                this.root = null;
            return;
        }

        Queue<BinTreeNode<T>> q = new LinkedList<>();
        q.add(this.root);
        BinTreeNode<T> temp = null, keyNode = null;

        while (!q.isEmpty()) {
            temp = q.peek();
            q.remove();

            if (temp.getKey() == key)
                keyNode = temp;

            if (temp.getLeft() != null)
                q.add(temp.getLeft());

            if (temp.getRight() != null)
                q.add(temp.getRight());
        }

        if (keyNode != null) {
            T x = temp.getKey();
            deleteDeepest(temp);
            keyNode.setKey(x);
        }
    }

    /**
     * Calculates the height of a given node in the binary tree.
     *
     * @param node The node for which the height is to be calculated.
     * @return The height of the specified node.
     */
    public int height(BinTreeNode<T> node) {
        if (node == null)
            return 0;
        else {
            int lHeight = this.height(node.getLeft());
            int rHeight = this.height(node.getRight());

            if (lHeight > rHeight)
                return lHeight + 1;
            return rHeight + 1;
        }

    }
}
