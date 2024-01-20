package org.restframework.complex.containers;

import lombok.*;
import org.jetbrains.annotations.NotNull;


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
