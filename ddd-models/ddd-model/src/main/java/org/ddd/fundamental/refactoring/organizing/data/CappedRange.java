package org.ddd.fundamental.refactoring.organizing.data;

public class CappedRange extends IntRange{

    private int cap;

    public CappedRange(int low, int high, int cap) {
        super(low, high);
        this.cap = cap;
    }

    public int getCap() {
        return cap;
    }

    //如果没有父类  IntRange的封装方法,getHigh是没有办法进行覆盖的
    @Override
    public int getHigh(){
        return Math.min(super.getHigh(), getCap());
    }
}
