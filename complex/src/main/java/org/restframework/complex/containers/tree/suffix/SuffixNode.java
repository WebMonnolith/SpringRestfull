package org.restframework.complex.containers.tree.suffix;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class SuffixNode {
    private String text;
    private List<SuffixNode> children;
    private int position;

    public SuffixNode(String word, int position) {
        this.text = word;
        this.position = position;
        this.children = new ArrayList<>();
    }
}
