package org.restframework.complex.containers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.Contract;

@EqualsAndHashCode(callSuper = true)
@Data
public class BinTreeNode<T> extends Node<BinTreeNode> {
    private T key;

    public BinTreeNode(T item) {
        this.key = item;
        this.left = this.right = null;
    }

    public boolean isLeaf() {
        return this.left != null && this.right != null;
    }

}
