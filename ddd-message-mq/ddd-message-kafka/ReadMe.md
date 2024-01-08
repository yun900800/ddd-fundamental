# kafka如何保证幂等性
首先简单介绍一下通常情况
1. 如果消息系统不能传递重复的消息,但是可能会丢失消息,那么这样的情况我们称为最多传递一次
2. 同理,如果消息系统不会丢失消息,但是存在传递重复消息的情况在,这就是所谓的最少传递一次
3. 既不会传递重复消息,也不会丢失消息,这种情况就是只有传递一次消息

起初的时候kafka只支持最多一次和最少一次的消息传递,引入了事务消息的概念以后,只传递一次功能kafka也支持。

## 什么是consume-transform-produce pattern
1. Sends a list of consumed offsets to the consumer group coordinator, 
  and also marks those offsets as part of the current transaction. 
  These offsets will be considered consumed only if the transaction is committed successfully. 
  This method should be used when you need to batch consumed and produced messages together, 
  typically in a consume-transform-produce pattern.
## 理解偏移量在消费中的作用
1. 如果还是以上的模型consume-transform-produce,如果集群不一致的话,那么需要使用
   applications that must read and write to different Kafka clusters 
   must use the older commitSync and commitAsync API;同时需要将偏移量记录
   在外部用于保证事务的读取