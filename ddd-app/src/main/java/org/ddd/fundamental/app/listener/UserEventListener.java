package org.ddd.fundamental.app.listener;

import org.ddd.fundamental.app.model.UserEvent;
import org.ddd.fundamental.app.model.UserModel;
import org.ddd.fundamental.app.repository.user.UserRepository;
import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserEventListener {

    private  static final Logger LOGGER = LoggerFactory.getLogger(UserEventListener.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DomainEventJsonDeserializer deserializer;

    private List<UserModel> userModelList = new ArrayList<>();

    private static final int  BATCH_NUM = 10;

    @RabbitListener(queues = "user.event")
    public synchronized void consume(Message message) throws Exception{
        String      serializedMessage = new String(message.getBody());
        UserEvent domainEvent       = (UserEvent)deserializer.deserialize(serializedMessage);
        String name = Thread.currentThread().getName();
        if (userModelList.size()<=BATCH_NUM) {
            LOGGER.info("thread name:{} start add data to userModelList.",name);
            UserModel userModel = new UserModel(domainEvent.getUserName(), domainEvent.getPassword());
            userModel.setId(domainEvent.aggregateId());
            userModelList.add(userModel);
        }
        if (userModelList.size() >= BATCH_NUM) {
            LOGGER.info("execute only once consume");
            userRepository.saveAll(userModelList);
            userModelList.clear();
        }
    }
    @RabbitListener(queues = "user.event")
    public synchronized void consume1(Message message) throws Exception{
        String      serializedMessage = new String(message.getBody());
        UserEvent domainEvent       = (UserEvent)deserializer.deserialize(serializedMessage);
        String name = Thread.currentThread().getName();
        if (userModelList.size()<=BATCH_NUM) {
            LOGGER.info("thread name:{} start add data to userModelList.",name);
            UserModel userModel = new UserModel(domainEvent.getUserName(), domainEvent.getPassword());
            userModel.setId(domainEvent.aggregateId());
            userModelList.add(userModel);
        }
        if (userModelList.size() >= BATCH_NUM) {
            LOGGER.info("execute only once consume1");
            userRepository.saveAll(userModelList);
            userModelList.clear();
        }
    }

//    @RabbitListener(queues = "user.event2")
//    public void consume2(Message message) throws Exception{
//        String      serializedMessage = new String(message.getBody());
//        UserEvent domainEvent       = (UserEvent)deserializer.deserialize(serializedMessage);
//
//        if (userModelList.size()<=BATCH_NUM) {
//            UserModel userModel = new UserModel(domainEvent.getUserName(), domainEvent.getPassword());
//            userModel.setId(domainEvent.aggregateId());
//            userModelList.add(userModel);
//        }
//        if (userModelList.size() >= BATCH_NUM) {
//            LOGGER.info("execute only once");
//            userRepository.saveAll(userModelList);
//            userModelList.clear();
//        }
//    }
//
//    @RabbitListener(queues = "user.event3")
//    public void consume3(Message message) throws Exception{
//        String      serializedMessage = new String(message.getBody());
//        UserEvent domainEvent       = (UserEvent)deserializer.deserialize(serializedMessage);
//
//        if (userModelList.size()<=BATCH_NUM) {
//            UserModel userModel = new UserModel(domainEvent.getUserName(), domainEvent.getPassword());
//            userModel.setId(domainEvent.aggregateId());
//            userModelList.add(userModel);
//        }
//        if (userModelList.size() >= BATCH_NUM) {
//            LOGGER.info("execute only once");
//            userRepository.saveAll(userModelList);
//            userModelList.clear();
//        }
//    }

//    @RabbitListener(queues = "user.event4")
//    public void consume4(Message message) throws Exception{
//        String      serializedMessage = new String(message.getBody());
//        UserEvent domainEvent       = (UserEvent)deserializer.deserialize(serializedMessage);
//
//        if (userModelList.size()<=BATCH_NUM) {
//            UserModel userModel = new UserModel(domainEvent.getUserName(), domainEvent.getPassword());
//            userModel.setId(domainEvent.aggregateId());
//            userModelList.add(userModel);
//        }
//        if (userModelList.size() >= BATCH_NUM) {
//            LOGGER.info("execute only once");
//            userRepository.saveAll(userModelList);
//            userModelList.clear();
//        }
//    }
}
