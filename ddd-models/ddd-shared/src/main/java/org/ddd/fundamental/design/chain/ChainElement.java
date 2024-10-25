package org.ddd.fundamental.design.chain;

import java.util.List;

public interface ChainElement<T>{
    void setNext(T element);

    T getNext();
    boolean acceptNext(T element);
    boolean acceptPre(T element);
    static <T extends ChainElement<T>> T buildChain(List<T> elements, T lastElement) {
        if (elements.isEmpty()) {
            return lastElement;
        }
        for (int i = 0; i < elements.size(); i++) {
            var current = elements.get(i);
            var next = i < elements.size() - 1 ? elements.get(i + 1) : lastElement;
            if (current.acceptNext(next) && next.acceptPre(current)) {
                current.setNext(next);
            } else {
                throw new RuntimeException("构建责任链失败");
            }
        }
        return elements.get(0);
    }
}
