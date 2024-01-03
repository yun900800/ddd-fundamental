# Character基础类常用API

1. Character.isLetter()判断是否为字母
2. Character.isDigit()判断字符是否为数字
3. Character.isWhiteSpace()
4. Character.isUpperCase()大写字母
5. Character.isLowerCase()小写字母
6. Character.toUpperCase()转大写字母
7. Character.toLowerCase()转小写字母
8. Character.toString()转字符串

# StringBuffer基础类常用API
1. 添加
    - append(String str)
    - insert(int offset,String str)
2. 删除
    - deleteCharAt(int index)
    - delete(int start,int end)
3. 替换
    - replace(int start,int end, String str)
4. 反转 
    - reverse()

# Integer,Long
1. Integer 转二进制
   - Integer.toBinaryString()
2. 二进制转十进制
   - Integer.parseInt(str,2)
# java中实现栈和队列可以使用一下两种方式:
```
   @Test
   public void testQueue() {
     LinkedList<String> queue = new LinkedList<>();
     queue.offer("hello");
     queue.offer("world");
     queue.offer("nice");
     queue.offer("girl");
     StringBuilder sb = new StringBuilder();
     while (!queue.isEmpty()) {
     String element = queue.peek();
     sb.append(element);
     queue.poll();
     }
     Assert.assertTrue(queue.isEmpty());
     Assert.assertEquals("helloworldnicegirl",sb.toString());
   }
   
   @Test
   public void testStack() {
     Deque<String> stack = new LinkedList<>();
     stack.push("a");
     stack.push("b");
     stack.push("c");
     stack.push("d");
     StringBuilder sb = new StringBuilder();
     while (!stack.isEmpty()) {
     String element = stack.peek();
     sb.append(element);
     stack.pop();
     }
     Assert.assertTrue(stack.isEmpty());
     Assert.assertEquals("dcba",sb.toString());
  }
```