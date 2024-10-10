# 为什么会有泛型,它的好处是什么

1. Holders1只能会有一种类型Automobile,类和方法不够通用
2. Holders2可以持有多种类型,任何类型都可以，但是在运行时容易出错,不能借助编译器来检查类型
3. Holders3编译时检查类型,运行时使用的时候确定类型,类型的范围在前两者之间;通用且不容易出错

4. That’s the core idea of Java generics: You tell it what type you want to use, and it takes care of
   the details.