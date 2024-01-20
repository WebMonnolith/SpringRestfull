package org.restframework.complex;

import lombok.Data;

import java.util.function.Function;

@Data
public class TreeNode {
    public Function<Double[], Boolean> question;
    private boolean isLeaf;
    private TreeNode left;
    private TreeNode right;
    private Double prediction;
}
