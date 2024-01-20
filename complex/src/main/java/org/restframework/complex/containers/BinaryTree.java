package org.restframework.complex.containers;

import lombok.*;


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
}
