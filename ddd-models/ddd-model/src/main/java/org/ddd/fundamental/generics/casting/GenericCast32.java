package org.ddd.fundamental.generics.casting;

public class GenericCast32 {
    public static final int SIZE = 10;
    public static void main(String[] args) {
        FixedSizeStack<String> strings =
                new FixedSizeStack<String>(SIZE);
        for(String s : "A B C D E F G H I J".split(" "))
            strings.push(s);
        // Runtime ArrayIndexOutOfBoundsException:
        //strings.push("oops"); // Bounds-checking required:
        if(strings.getIndex() < SIZE) strings.push("oops");
        for(int i = 0; i < SIZE; i++) {
            String s = strings.pop();
            System.out.print(s + " ");
        }
    }

}
