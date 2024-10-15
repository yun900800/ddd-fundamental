package org.ddd.fundamental.generics.latent.adapter;

import org.ddd.fundamental.generics.latent.SimpleQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Fill {
    public static <T> void fill(Collection<T> cons,Class<? extends T> type, int size){
        for (int i = 0; i < size; i++) {
            try {
                cons.add(type.newInstance());
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Contract {
    private static long counter = 0;
    private final long id = counter++;
    public String toString() {
        return getClass().getName() + " " + id;
    }
}
class TitleTransfer extends Contract {}

class FillTest {
    public static void main(String[] args) {
        List<Contract> contracts = new ArrayList<Contract>();
        Fill.fill(contracts, Contract.class, 3);
        Fill.fill(contracts, TitleTransfer.class, 2);
        for(Contract c: contracts)
            System.out.println(c);
        SimpleQueue<Contract> contractQueue =
                new SimpleQueue<Contract>();
        // Won’t work. fill() is not generic enough:
        // 这里SimpleQueue有add方法没有实现Collection接口，因此不能使用
        // Fill.fill(contractQueue, Contract.class, 3);
    }
}
