package org.ddd.fundamental.algorithm.basedata.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericsAndCovariance {

    public static void main(String[] args) {
        testArrayGenerics();
    }

    /**
     * java中数组是协变的,虽然编译的时候不会报错,但是运行时候类型不对会报错
     *
     */
    public static void testArrayGenerics(){
        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();
        //fruits[1] = new Orange();
        //fruits[2] = new Jonathan();
        try {
            fruits[0] = new Fruit();
        } catch (Exception e){
            System.out.println(e);
        }
        try {
            fruits[0] = new Orange();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * 此处编译会报错,因为泛型不是协变的
     */
    public static void nonCovariantGenerics() {
//        List<Fruit> fruitList = new ArrayList<Apple>();
    }

    /**
     * 1. 使用 <? extends Fruit>时候,需要将尖括号内部分整体看,因此它定义了
     * 一个类型S ,S必须是Fruit的子类,但是S不确定,可以是Apple，Orange,Object
     * 因此在编译时候添加这些对象会报错,就是因为S不确定
     * 2. 但是这里却实现了将List<Apple> --> List<? extends Fruit>的类型转换,其实就是协变
     *  <Apple> extends <? extends Fruit>
     *  List<Apple> --> List<? extends Fruit>
     *
     */
    public static void genericsAndCovariance(){
        List<? extends Fruit> fruits = new ArrayList<Apple>();
//        fruits.add(new Apple());  // 编译错误
//        fruits.add(new Fruit());  // 编译错误
//        fruits.add(new Object());  // 编译错误
    }

    /**
     * 1. <? super Apple> 这个类型假设为S而S的下界是Apple,显然Apple的子类是可以放入的
     * @param apples
     */
    public static void superTypeWildcards(List<? super Apple> apples) {
        //这里是用apples消费数据,能够显示的最大类型是Apple
        apples.add(new Apple());
        apples.add(new Jonathan());
//        apples.add(new Fruit());  // 编译错误
    }

    /**
     * 理解什么是PECS producer --> extends; consumer --> super
     * @param apples
     */
    public static void extendTypeWildcards(List<? extends Apple> apples) {
        //这里是从apples中拿取数据,能够限制的最小类型是Apple
        Apple apple = apples.get(0);
//        Jonathan jonathan = apples.get(0);  // 编译错误
        Fruit fruit = apples.get(0);
    }

}

class Fruit {}
class Apple extends Fruit {}
class Jonathan extends Apple {}
class Orange extends Fruit {}
