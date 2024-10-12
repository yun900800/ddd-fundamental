package org.ddd.fundamental.generics.bounds;

/**
 * 这个参数泛型T的范围是全部对象
 * @param <T>
 */
class HoldItem<T> {
    T item;
    HoldItem(T item) { this.item = item; }
    T getItem() { return item; }
}

/**
 * 这个参数泛型T的范围上界是HasColor
 * @param <T>
 */
class Colored2<T extends HasColor> extends HoldItem<T> {
    Colored2(T item) { super(item); }
    java.awt.Color color() { return item.getColor(); }
}

/**
 * 这个参数泛型T的范围上界是HasColor & Dimension
 * @param <T>
 */
class ColoredDimension2<T extends Dimension & HasColor>
        extends Colored2<T> {
    ColoredDimension2(T item) { super(item); }
    int getX() { return item.x; }
    int getY() { return item.y; }
    int getZ() { return item.z; }
}

/**
 * 这个参数泛型T的范围上界是HasColor & Dimension & Weight
 * @param <T>
 */
class Solid2<T extends Dimension & HasColor & Weight>
        extends ColoredDimension2<T> {
    Solid2(T item) { super(item); }
    int weight() { return item.weight(); }
}

public class InheritBounds {
    public static void main(String[] args) {
        Solid2<Bounded> solid2 =
                new Solid2<>(new Bounded());
        solid2.color();
        solid2.getY();
        solid2.weight();
    }
}
