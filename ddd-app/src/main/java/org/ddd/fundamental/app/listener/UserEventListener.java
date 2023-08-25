package org.ddd.fundamental.app.listener;

import org.ddd.fundamental.app.model.UserEvent;
import org.ddd.fundamental.app.model.UserModel;
import org.ddd.fundamental.app.repository.user.UserRepository;
import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventJsonDeserializer;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class UserEventListener {

    private  static final Logger LOGGER = LoggerFactory.getLogger(UserEventListener.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DomainEventJsonDeserializer deserializer;

    @Autowired
    private RabbitMqDomainEventsConsumer consumer;

    private List<UserModel> userModelList = new ArrayList<>();

    private static final int  BATCH_NUM = 2000;

    private ExecutorService service = Executors.newFixedThreadPool(4);

    @RabbitListener(queues = "user.event")
    public synchronized void consume(Message message) throws Exception{
        String      serializedMessage = new String(message.getBody());
        UserEvent domainEvent       = (UserEvent)deserializer.deserialize(serializedMessage);
        String name = Thread.currentThread().getName();
        if (userModelList.size()<=BATCH_NUM) {
            LOGGER.info("thread name:{} start add data to userModelList.",name);
            LOGGER.info("LOGGER:size{}",userModelList.size());
            UserModel userModel = new UserModel(domainEvent.getUserName(), domainEvent.getPassword());
            userModel.setId(domainEvent.aggregateId());
            userModelList.add(userModel);
        }
        if (userModelList.size() >= BATCH_NUM) {
            LOGGER.info("execute only once consume");
            List<UserModel> clone = deepCopyUsingCloneable(userModelList);
            service.submit(new UserBatchWorker(clone,userRepository));
            //userRepository.saveAll(userModelList);
            userModelList.clear();
        }
    }
    @RabbitListener(queues = "user.event")
    public synchronized void consume1(Message message) throws Exception{
        //这里消费消息慢的原因是数据库没有分开
        String      serializedMessage = new String(message.getBody());
        UserEvent domainEvent       = (UserEvent)deserializer.deserialize(serializedMessage);
        String name = Thread.currentThread().getName();
        if (userModelList.size()<=BATCH_NUM) {
            LOGGER.info("thread name:{} start add data to userModelList.",name);
            LOGGER.info("LOGGER:size{}",userModelList.size());
            UserModel userModel = new UserModel(domainEvent.getUserName(), domainEvent.getPassword());
            userModel.setId(domainEvent.aggregateId());
            userModelList.add(userModel);
        }
        if (userModelList.size() >= BATCH_NUM) {
            LOGGER.info("execute only once consume1");
            List<UserModel> clone = deepCopyUsingCloneable(userModelList);
            service.submit(new UserBatchWorker(clone,userRepository));
            //userRepository.saveAll(userModelList);
            userModelList.clear();
        }
    }

    @RabbitListener(queues = {"org.ddd.fundamental.app.listener.user_event_rabbit_listener"})
    public void consumeError(Message message) throws Exception {
        String      serializedMessage = new String(message.getBody());
        UserEvent domainEvent       = (UserEvent) deserializer.deserialize(serializedMessage);
        LOGGER.info("UserEventListener UserEvent:{}",domainEvent);
        throw new RuntimeException("rabbit error handler");
    }

    @RabbitListener(queues = "pizza-message-queue.dlq")
    public void errorHandler(Message message)  {
        String msg = new String(message.getBody());
        LOGGER.info("errorHandler msg:{}",msg);
    }

    public static List<UserModel> deepCopyUsingCloneable(List<UserModel> userModelList){
        return userModelList.stream().map(UserModel::clone).collect(Collectors.toList());
    }
}
