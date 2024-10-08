### 复杂事物的简单立即

#### 亮在荆州，以建安初与颍川石广元、徐元直、汝南孟公威等俱游学，三人务於精熟，而亮独观其大略。
——《三国志》
1. 这段话说明诸葛亮更关注的是大略,而其他三人更关注细节
2. 有了以上两类人的比较,我们可以得出一种‘按愿望思考(Wishful thinking)’
    的思维
3. 这点不仅是编程的要点，也是有助于生活的一种思维方式。

当在思考的过程中，如果在过程中的某一步过分纠缠细节，很容易迷失了大方向，降低思考的效率。此时不妨无视掉这些繁杂的细节，假设这个过程能在输入下返回期望的结果（接口），不理会其具体的实现，继续进行下一步的思考。
4. 分层设计（自顶而下）
   基于按愿望思考，我们可以在设计程序时，假设出当前层次的各个过程、方法，暂时先不理会它们的具体实现。当在设计好接口后、具体实现这些过程，也不必理会上层是如何使用。这样编程的精力能集中在当前层次的模块中。抽象是控制复杂度的银弹，让人能够用有限的脑力理解复杂的大型工程。
   良好的分层设计同时也便于改进，当需要修复某一层的bug、或者优化某一层的效率时，该层的上下层无需做太多的改动。分层设计良好的项目生命力更持久。
5. 感悟
   过去的我曾有一个思维误区，就是感觉有挑战的问题必定有精妙复杂的解，靠苦思冥想和灵光一现可以找到。导致经常会深陷在问题的各种细节中。看了SICP的前两章，开始意识到将复杂的东西抽象、简化才是人类理解事物的『大杀器』。
   遇见一个复杂的事物无法理解，不必感到沮丧，可以尝试从自己可以理解的简单角度理解。最简单的，输入不同的信息，观察其会有怎样的输入。这是一种基于实用主义的理解。
   甚至更夸张的，不去理解这些复杂事物，看能否直接绕过。有些问题不去面对，可能问题本身就消失了。

6. 如何确定使用引用还是使用值对象？https://softwareengineering.stackexchange.com/questions/435671/better-to-store-references-to-an-object-or-to-store-its-id-and-retrieve-it-with
   - 这里牵涉到一些模式,比如identity map, repository pattern, even recommended between aggregates in DDD
7. 如何理解递归，这是一篇不错的文章,主要讲述了递归的基本概念,递归需要注意的事项,以及递归通常用在哪些场景？
   https://techsauce.medium.com/learning-recursion-a-deep-dive-into-an-essential-computer-science-concept-7f88c07d6427
8. [CPS在java中的应用](https://javatechonline.com/continuation-passing-style-cps-in-java/)

9. recursive five steps:
   - fundamental problem : 对前n个自然数求和
   - challenging problem : 对于一个n*m的方格，从左上到右下的唯一路劲的数目,每次向右或者下移动一个单元格
      1. 这个问题的base case是 grid_path(n,1) = 1 grid_path(1,m) = 1;
      2. grid_path(n,m) = grid_path(n,m-1) + grid(n-1,m)
   - hardest problem : n个物体分组，每组最多m个物体,m>=0 m<=n ,有多少中分组方式
      1. count_partitions(n,m) = 1 if n == 0 ; count_partitions(n,m) = 0 if m == 0
      2. count_partitions(n,m) = count_partitions(n,m-1) + count_partitions(n-m,m)
   - step blew
      1. What's the simplest possible input?(base case)
      2. Play around with examples and visualize?
      3. Relate hard cases to simpler cases.
      4. Generalize the pattern.
      5. Write code by combining recursive pattern with the base case.
10. 增加一个例子说明在一个微服务内部各个领域之间如何交互从而实现一个具体的申请VISA和检查各种资质的具体例子;[文章链接](https://medium.com/@ygnhmt/a-soft-introduction-to-domain-driven-design-from-theory-to-java-code-implementation-part-2-5aa7e1cfef65),主要说明如下:
    - 这里主要关注领域模型和事件的设计
    - 接口的设计一定要简介并且不要污染领域模型
    - 这里有一个问题就是securityCheck这个聚合根注入了visaApplication这个聚合根,是否合理的问题
    - 关于jps和jms的实现问题没有关注细节
    - 这里的交互其实涉及到四个领域VisaApplication,SecurityCheck,Security Microservice,VisaIssue domain;这里一定要理解清除它们之间的关系
    - 注意这里将领域事件分成了两个层次，一个是同一个微服务内部不同领域模型之间的事件,另一个就是不同微服务之间的事件

11. 注意在这里工序的流转实际上是一个状态机,需要理解状态模式和状态机模式之间的区别和关系;以及两个不同状态机,spring和cola状态机的区别以及
    为什么要这样设计?