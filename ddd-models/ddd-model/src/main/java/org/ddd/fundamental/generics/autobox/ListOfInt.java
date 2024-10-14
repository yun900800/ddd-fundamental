package org.ddd.fundamental.generics.autobox;

import java.util.*;

public class ListOfInt {
    public static void main(String[] args) {
        List<Integer> li = new ArrayList<Integer>();
        for(int i = 0; i < 5; i++)
            li.add(i);
        for(int i : li)
            System.out.print(i + " ");

        Byte[] possibles = { 1,2,3,4,5,6,7,8,9 };
        Set<Byte> mySet =
                new HashSet<Byte>(Arrays.asList(possibles));
        // But you can’t do this:
//         Set<Byte> mySet2 = new HashSet<Byte>(
//         Arrays.<Byte>asList(1,2,3,4,5,6,7,8,9));
    }
}
