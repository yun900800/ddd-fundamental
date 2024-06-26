package org.ddd.fundamental.app.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ddd.fundamental.app.api.client.OkHttpClientUtils;
import org.ddd.fundamental.app.listener.UserEventListener;
import org.ddd.fundamental.app.model.UserEvent;
import org.ddd.fundamental.app.model.UserModel;
import org.ddd.fundamental.app.model.UserOldModel;
import org.ddd.fundamental.app.note.model.Order;
import org.ddd.fundamental.app.note.repository.OrderRepository;
import org.ddd.fundamental.app.repository.user.UserOldRepository;
import org.ddd.fundamental.app.repository.user.UserRepository;
import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.UuidGenerator;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;
import org.hsqldb.rights.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserService {

    private  static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final double AMOUNT = 50.00;

    @Autowired
    private UserRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserOldRepository userOldRepository;

    @Autowired
    private UuidGenerator uuidGenerator;

    @Autowired
    private RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    private RabbitMqDomainEventsConsumer consumer;

    private ExecutorService service = Executors.newFixedThreadPool(10);

    @Transactional
    public void batchRegister(List<UserModel> userModelList) {
        repository.saveAll(userModelList);
    }


    @Transactional(transactionManager = "transactionManager")
    public void registerUser(String name, String password) {
        UserModel model = new UserModel(name, password);
        model.setId(uuidGenerator.generate());
//        UserOldModel oldModel =new UserOldModel(name,password);
//        oldModel.setId(domain.getId());
//        userOldRepository.save(oldModel);

        rabbitMqPublisher.publish(new UserEvent(name,password,model.getId()),"domain_events");
        //consumer.consume();
    }
    @Transactional(transactionManager = "transactionManager")
    public void asyncRegisterUser() {
        long startTime = System.currentTimeMillis();
        for (int i = 0 ;i <100000; i++) {
            service.submit(()->{
                String userName = uuidGenerator.generate();
                String password = userName.substring(0,20);
                try {
                    String json = OkHttpClientUtils.asyncRegister(userName,password);
                    String url = "http://localhost:9000/register";
                    String ret = OkHttpClientUtils.post(url,json);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        long cost = (System.currentTimeMillis() - startTime)/1000;
        LOGGER.info("请求处理时间:"+cost+"s");
    }


    @Transactional(transactionManager = "transactionManager")
    public void errorRegisterUser() {
        service.submit(()->{
            String userName = uuidGenerator.generate();
            String password = userName.substring(0,20);
            try {
                String json = OkHttpClientUtils.asyncRegister(userName,password);
                String url = "http://localhost:9000/register";
                String ret = OkHttpClientUtils.post(url,json);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Transactional(transactionManager = "transactionManager")
    public void saveUserModel(String name, String password) {
        UserModel model = new UserModel(name, password);
        model.setId(uuidGenerator.generate());
        repository.save(model);
    }

    @Transactional(transactionManager = "note-transactionManager")
    public void saveOrder(String name) {
        Order order = new Order(name,AMOUNT);
        order.setId(uuidGenerator.generate());
        orderRepository.save(order);
    }
}
