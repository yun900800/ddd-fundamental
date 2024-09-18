package org.ddd.fundamental.algorithm.basedata.generic.advanced;

import java.util.ArrayList;
import java.util.List;

public class Container<T> {
    private final T value;

    private List<Integer> numbersList;

    Container(T value){
        this.value = value;
    }

    public List<Integer> getNumbers() {
        return numbersList;
    }

    /**
     * 这里会编译错误,因为原始类型的使用不仅会删除泛型的信息,也会删除预定义的信息
     * 因此这里的List<Integer> 实际上就是List
     * @param container
     */
//    public void traverseNumbersList(Container container) {
//        for (int num : container.getNumbers()) {
//            System.out.println(num);
//        }
//    }

    /**
     * 如果不关注泛型参数,可以使用通配符?
     * @param container
     */
    public void traverseNumbersList(Container<?> container) {
          for (int num : container.getNumbers()) {
              System.out.println(num);
          }
      }

    /**
     * 数组是协变而泛型不是
     * 数据协变在运行时存在bug
     */
    public void arrayDifferentiateGeneric(){
        Number[] nums = new Long[3];
        nums[0] = 1L;
        nums[1] = 2L;
        nums[2] = 3L;
        Object[] objs = nums;
        objs[2] = "ArrayStoreException happens here";

        List<Number> nums1 = new ArrayList<Number>();
        List<Long> longs = new ArrayList<Long>();
        //nums1 = longs;   // compilation error
    }
}
