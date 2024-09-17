package org.ddd.fundamental.refactoring.organizing.data;

/**
 * 当定义的属性需要扩展的时候,访问这个属性最好采用方法来进行间接访问
 * 这样方便子类进行扩展
 */
public class IntRange {

    private int low;

    private int high;

    IntRange(int low, int high){
        initialize(low, high);
    }

    public boolean includes(int arg){
        return arg >= getLow() && arg <= getHigh();
    }

    public void grow(int factor) {
        setHigh(getHigh() * factor);
    }

    private void initialize(int low, int high){
        this.low = low;
        this.high = high;
    }


    // 如果没有set和get方法,子类扩展无法进行

    int getLow() {
        return low;
    }

    void setLow(int low) {
        this.low = low;
    }

    int getHigh() {
        return high;
    }

    void setHigh(int high) {
        this.high = high;
    }
}
