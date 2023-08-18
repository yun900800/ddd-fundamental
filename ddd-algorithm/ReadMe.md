# String 基础知识
1. == 比较分为两种情况 基本类型(值比较)和引用类型(地址比较),而equal比较的是字符序列的值
     ```
    @Test
    public void testDemo1() {
        String str1 = "abc";
        String str2 = "abc";
        Assert.assertTrue(str1 == str2);
        Assert.assertTrue(str1.equals(str2));
    }
    ```
   ![demo1.png](img.png)
2. 下面的代码创建了几个对象
    ```
   String str1 = new String("abc")
   ```
   new 出来的对象都是在堆内存, 因此str1在堆内存,而"abc"指向常量池
   ![demo2.png](img_1.png)
3. 看下面的代码
    ```
   @Test
    public void testDemo3() {
        String str1 = new String("abc");
        String str2 = "abc";
        Assert.assertFalse(str1 == str2);
        Assert.assertTrue(str1.equals(str2));
    }
   ```
   ![demo3.png](img_2.png)
4. 判断下面的str1和str2是否相等
    ```
    @Test
    public void testDemo4() {
        String str1 = "a"+"b"+"c";
        //在常量池创建"abc"
        String str2 = "abc";
        //赋值以后比较
        Assert.assertTrue(str1 == str2);
        Assert.assertTrue(str1.equals(str2));
    }
   ```
5. 判断下面的字符比较
    ```
    @Test
    public void testDemo5() {
        String str1 = "ab";
        String str2 = "abc";
        String str3 = str1+ "c";
        String str4 = "ab"+"c";
        Assert.assertTrue(str2 == str4);
        Assert.assertFalse(str2 == str3);
        Assert.assertTrue(str2.equals(str3));
    }
   ```
   字符串字面量的拼接会直接存储在常量池中,如果有变量的话会使用StringBuilder拼接
   ![demo5.png](img_3.png)

6. 总结:
    - 什么时候拼接是在常量池中,什么时候拼接使用StringBuilder?
    - == 比较和 equal比较的区别?
    - new String("abc")和 new String("abc").intern()有啥区别;

7. 快速排序思维总结
    - 实际上是在二叉树的前序位置,找到某个元素的位置
    - 递归结束的条件是当begin==end的时候,即区间只有一个元素(显然如果有两个元素,partition函数是可以执行的)
    - partition的时候,任选一个元素pivot,如果比这个元素小,就从左边的第一个元素开始替换,找到全部比pivot小的时候元素
    记录下个数索引,然后将索引+i的元素与pivot替换就好;
    - 这里有一个细节,如果选取的最后一个元素作为pivot,那么直接替换就好,并且比较的时候不用和最后一个元素比较
   如果是第一个元素,则可以从第二个元素开始比较,但是因为pivot可能被替换走,所以需要记录替换以后这个pivot的索引,
   便于最后的替换;
    - 理解了这一层,我们可以从数组中任取一个元素,只是需要多一个变量来记录替换的索引