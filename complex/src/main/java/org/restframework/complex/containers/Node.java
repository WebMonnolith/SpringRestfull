package org.restframework.complex.containers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Node <T> {
    /**
     * The left subtree of the node. Null for leaf nodes.
     */
    protected T left;

    /**
     * The right subtree of the node. Null for leaf nodes.
     */
    protected T right;
}
