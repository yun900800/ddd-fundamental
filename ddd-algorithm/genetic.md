# 理解java中的协变

1. 数组是协变的,泛型不是协变的:
   逆变与协变用来描述类型转换（type transformation）后的继承关系，其定义：如果A、B表示类型，f(⋅)表示类型转换，≤表示继承关系（比如，A≤B表示A是由B派生出来的子类）
   f(⋅)是逆变（contravariant）的，当A≤B时有f(B)≤f(A)成立；
   f(⋅)是协变（covariant）的，当A≤B时有f(A)≤f(B)成立；
   f(⋅)是不变（invariant）的，当A≤B时上述两个式子均不成立，即f(A)与f(B)相互之间没有继承关系。

   1. java泛型不是协变的,如何解决这个问题呢?
       - 首先我们需要明白的是,java的泛型类型在运行的时候被擦除掉了,因此，下面的操作编译器会报错
      ```java
       public class Erased<T> {
           private final int SIZE = 100;
           public static void f(Object arg) {
               if (arg instanceof T) {}  // 编译错误
               T var = new T();  // 编译错误
               T[] array = new T[SIZE];  // 编译错误
           }
       }
      ```
      判断泛型的类型是不行的;创建泛型类型对象是不行的;创建泛型数组是不行的
      - 如何解决以上问题呢？
      首先,可以通过class.isInstance()方法来进行判断;第二个问题的解决方案是使用工厂方法;第三个问题
      的解决方案是this.array = (T[])Array.newInstance(clazz,size);有一点需要记住的是泛型信息
      不是完全擦除,而是记录到类的字段元数据中,通过反射可以拿到
2. [理解什么是PECS](https://www.baeldung.com/java-generics-pecs),其实就是生产数据的时候用extend,消费数据的时候用super
   ```
   ListIterator<? super T> di=dest.listIterator();
   ListIterator<? extends T> si=src.listIterator();
   ```
   以上两段代码是从src复制数据到dest;可以看出生产数据方式从src来,从这里来的数据一定是T的子类,
   而dest的数据的下界是T,因此src的数据一定可以放到dest中
3. [什么是自限定类型](https://blog.csdn.net/anlian523/article/details/102511783)
   ```java
   class SelfBounded<T extends SelfBounded<T>> { 
   }
   ```
   - 理解自限定类型是怎么推理出上面的代码类型的   

   它的作用:定义一个基类,这个基类可以使用子类作为参数,返回类型，作用域
   - 它的本质是基类用子类代替其参数,这意味着泛型基类变成了一种其所有子类的公共功能模版，
   但是在所产生的类中将使用确切类型而不是基类型。