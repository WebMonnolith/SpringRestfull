import org.junit.jupiter.api.Test;
import org.restframework.complex.containers.BinTreeNode;
import org.restframework.complex.containers.BinaryTree;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinaryTree {

    @Test
    public void createA_SimpleBinaryTree() {
        BinaryTree<Integer> tree = new BinaryTree<>();

        // Create root
        tree.setRoot(new BinTreeNode<>(1));
        /* Following is the tree after above statement
           1
          / \
        null null
        */

        tree.getRoot().setLeft(new BinTreeNode<>(2));
        tree.getRoot().setRight(new BinTreeNode<>(3));
        /* 2 and 3 become left and right children of 1
              1
             / \
            2   3
           / \ / \
       null null null null */

        assertEquals(tree.getRoot().getKey(), 1);
        assertEquals(tree.getRoot().getLeft().getKey(), 2);
        assertEquals(tree.getRoot().getRight().getKey(), 3);
        System.out.println("Complete");

    }
}
