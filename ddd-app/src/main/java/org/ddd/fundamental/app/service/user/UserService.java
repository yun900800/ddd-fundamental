package org.ddd.fundamental.app.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ddd.fundamental.app.api.client.OkHttpClientUtils;
import org.ddd.fundamental.app.listener.UserEventListener;
import org.ddd.fundamental.app.model.UserEvent;
import org.ddd.fundamental.app.model.UserModel;
import org.ddd.fundamental.app.repository.user.UserRepository;
import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.UuidGenerator;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;
import org.hsqldb.rights.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserService {

    private  static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private UuidGenerator uuidGenerator;

    @Autowired
    private RabbitMqPublisher rabbitMqPublisher;

    private ExecutorService service = Executors.newFixedThreadPool(10);


    @Transactional
    public void registerUser(String name, String password) {
        UserModel model = new UserModel(name, password);
        model.setId(uuidGenerator.generate());
        //repository.save(model);

        rabbitMqPublisher.publish(new UserEvent(name,password,model.getId()),"domain_events");
    }
    @Transactional
    public void asyncRegisterUser() {
        long startTime = System.currentTimeMillis();
        for (int i = 0 ;i <10; i++) {
            service.submit(()->{
                String userName = uuidGenerator.generate();
                String password = userName.substring(0,20);
                try {
                    String json = OkHttpClientUtils.asyncRegister(userName,password);
                    String url = "http://localhost:9000/register";
                    String ret = OkHttpClientUtils.post(url,json);
                } catch (Exception e){
                }
            });
        }
        long cost = (System.currentTimeMillis() - startTime)/1000;
        LOGGER.info("请求处理时间:"+cost+"s");
    }
}
