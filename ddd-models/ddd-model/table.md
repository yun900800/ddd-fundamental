# 关于领域模型与存储层的思考

1. 这里的关系中,VisaApplication与Address是多对一的关系,因此在多方建立了一个外键
    ![img.png](img.png)
    这里的多对一和一对一关系都建立了外键,因此可以确定只有当四个表的数据约束都满足(都有数据时)时,才会创建
    VisaApplication这个表

2. 关于状态模式和状态机模式之间的区别理解，可以看看[这篇文章](https://medium.com/nerd-for-tech/the-super-state-design-pattern-166127ce7c9a)