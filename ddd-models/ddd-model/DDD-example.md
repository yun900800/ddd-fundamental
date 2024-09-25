# 一个具体的使用hibernate来实现领域驱动设计的例子
1. 文章的[链接地址](https://dev.to/kirekov/rich-domain-model-with-hibernate-445k?signin=true)如下
2. [git地址](https://github.com/SimonHarmonicMinor/rich-domain-model-with-hibernate-example)如下

3. 学习这个例子的总结:
    - 如果使用anemic domain model,服务层需要承载太多的业务逻辑;服务层需要知道实体层的很多信息
   如果服务层能够简单的调用领域层的api接口,这样是最好不过啦。anemic domain model的优点是简单,缺点是
   一旦业务复杂,可扩展性不强,因为这是面向过程,而不是面向对象;违背了开闭原则;有可能存在违反一致性约束,因为这样的约束
   的控制存在于service方法中,而不是充血模型的聚合根中;另一个问题是service层缺少封装,这一点与业务方法总是应该放置
   在最适合的业务对象中相违背；最后一点是测试不方便，甚至很难测试
    - Rich domain model能解决上面存在的很多问题,比如将业务职责放在合适的业务对象上，方便测试
    - 如果在领域模型上每一个域都设置了setter方法,那么显然是需要检查验证不变形约束,显然这不是一个
   好主意。因此在领域模型上最好只设定必要的操作,而不是每一个域都有setter;它的坏处在于违背了一致性约束
   因为很多人都不会检查setter对象的字段;如果少了约束,那么只能将检查放置在外部，而实际上这部分的责任应该
   在领域模型内部;字段越多，封装检查越难;如果真的希望修改属性，不要使用setter,而是使用changeXXX，这个
   方法更加容易理解;另外特别是对于一些有业务意义的字段,比如Pocket的status字段,如果处于Pending,那么是不能修改的
   封装在领域方法中，更容易处理，而且能减少冲突;
    - hibernate中实体需要一个无参构造器，其实这是一个不好的设计；不过可以将这个构造器设置成protected;
   创建对象的时候通过工厂方法来创建
    - 下面看看getter是怎么样破坏封装和产生不必要的检查,如果status有了getter方法,那么很有可能在外部service检查
   这个status,然后执行相关的操作，而实际上这部分代码的职责在领域内；这就是暴露getter的问题
    - 记住三点
        1. No-args constructor.
        2. Setters.
        3. Getters.
    - 将Tamagotchi类设置成包级别的访问，这样及时暴露公开的getter,别人也访问不到，气死你
   这样的封装才有意义;保证了只能通过聚合根来访问Tamagotchi
    - 检查Tamagotchi是否存在重复的时候有两种方案,数据少的时候直接读取全部数据然后再一个集合中判断重复
   数据多的时候就在外部服务中检查;
    - 同理更新某个具体Tamagotchi的时候可以先查出聚合根中指定的主表和一个具体明细表来更新,这种方法就要配合
   外部服务的重名检查;数据少的话可以直接拿全部数据,然后再更新

4. 学习值对象的知识总结:
   - Value object has to be immutable.
   - Value object should be comparable (i.e. implements equals/hashCode).
   - Value object guarantees that it always holds the correct value.
   - 值对象的好处:1、见类型知意义;2、包含具体的领域逻辑;3、通过检查保证值的合法性
5. 一个关于DDD的分享[技术会议](https://speakerdeck.com/robertbraeutigam/object-oriented-domain-driven-design?slide=40)
   另外一篇关于DDD中Repository如何设计的文章？看[这篇文章](https://softwareengineering.stackexchange.com/questions/386901/ddd-meets-oop-how-to-implement-an-object-oriented-repository)
   Repository相比于DAO的一个优势是查询的时候可以传递一个规约接口Specification,如果不使用这个接口就会写很多接口方法和实现。

6. 这篇文章中的[newBuilder模式](https://javajazzle.wordpress.com/2011/06/13/the-new-builder-pattern/)增加了一个验证的参数,避免对对象的修改

7. 怎么确定一个实体是聚合根还是本地实体？注意，并不一定在父子关系中父实体就是聚合根,子实体就是本地实体。
   How is the entity going to be accessed in the application?
   If the entity will be looked up by ID or through some kind of search it is probably an aggregate root.
   Will other aggregates need to reference it?
   If the entity will be referenced from within other aggregates it is definitely an aggregate root.
   How is the entity going to be modified in the application?
   If it can be modified independently it is probably an aggregate root.
   If it cannot be modified without making changes to another entity it is probably a local entity.