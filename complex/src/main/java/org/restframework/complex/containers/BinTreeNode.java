package org.restframework.complex.containers;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BinTreeNode extends Node<BinTreeNode> {
    private int key;

    public BinTreeNode(int item) {
        this.key = item;
        this.left = this.right = null;
    }
}
