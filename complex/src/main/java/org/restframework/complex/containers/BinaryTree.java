package org.restframework.complex.containers;

import lombok.*;

import java.util.LinkedList;
import java.util.Queue;


@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class BinaryTree <T> {

    @Getter
    @Setter
    private BinTreeNode<T> root;


    public BinaryTree(T key) {
        this.root = new BinTreeNode<>(key);
    }

    public void inorder(BinTreeNode<T> temp) {
        if (temp == null)
            return;

        this.inorder(temp.getLeft());
        System.out.print(temp.getKey() + " ");
        this.inorder(temp.getRight());
    }

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
