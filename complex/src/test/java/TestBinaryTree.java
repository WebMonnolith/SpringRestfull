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

    @Test
    public void binaryTreeHeight() {
        BinaryTree<Integer> tree = new BinaryTree<>(1);

        tree.getRoot().setLeft(new BinTreeNode<>(2));
        tree.getRoot().setRight(new BinTreeNode<>(3));
        tree.getRoot().getLeft().setLeft(new BinTreeNode<>(4));
        tree.getRoot().getLeft().setRight(new BinTreeNode<>(5));

        assertEquals(tree.height(tree.getRoot()), 3);
        System.out.println("Height of tree is " + tree.height(tree.getRoot()));
    }

    @Test
    public void testBinaryTreeInsertion() {
        BinaryTree<Integer> tree = new BinaryTree<>(1);

        tree.getRoot().setLeft(new BinTreeNode<>(2));
        tree.getRoot().setRight(new BinTreeNode<>(3));
        tree.getRoot().getLeft().setLeft(new BinTreeNode<>(4));
        tree.getRoot().getLeft().setRight(new BinTreeNode<>(5));

        System.out.print("Inorder traversal before insertion:");
        tree.inorder(tree.getRoot());

        tree.insert(tree.getRoot(), 12);

        System.out.print("Inorder traversal after insertion:");
        tree.inorder(tree.getRoot());
    }

    @Test
    public void testBinaryTreeDeletion() {
        BinaryTree<Integer> tree = new BinaryTree<>(1);

        tree.getRoot().setLeft(new BinTreeNode<>(2));
        tree.getRoot().setRight(new BinTreeNode<>(3));
        tree.getRoot().getLeft().setLeft(new BinTreeNode<>(4));
        tree.getRoot().getLeft().setRight(new BinTreeNode<>(5));

        System.out.print("Inorder traversal before deletion:");
        tree.inorder(tree.getRoot());

        int key = 5;
        tree.delete(key);

        System.out.print("\nInorder traversal after deletion:");
        tree.inorder(tree.getRoot());
    }
}
