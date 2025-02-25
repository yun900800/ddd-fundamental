package org.ddd.fundamental.material.hierarchy;

@FunctionalInterface
public interface TraverseNode<NODE,DATA> {
    DATA handle(DATA data,NODE node);
}
