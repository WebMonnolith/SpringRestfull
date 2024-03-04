package org.restframework.complex.containers.tree.suffix;

import org.restframework.complex.containers.ComplexContainer;

public class SuffixTree extends ComplexContainer<SuffixTree> {
    private static final String WORD_TERMINATION = "$";
    private static final int POSITION_UNDEFINED = -1;
    private SuffixNode root;
    private String fullText;

    public SuffixTree(String text) {
        root = new SuffixNode("", POSITION_UNDEFINED) {};
        fullText = text;
    }
}
