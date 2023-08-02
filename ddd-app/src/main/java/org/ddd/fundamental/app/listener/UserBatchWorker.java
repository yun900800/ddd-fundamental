package org.ddd.fundamental.app.listener;

import org.ddd.fundamental.app.model.UserModel;
import org.ddd.fundamental.app.repository.user.UserRepository;

import java.util.List;

public class UserBatchWorker implements Runnable{

    private List<UserModel> userModelList;

    private UserRepository userRepository;

    public UserBatchWorker(List<UserModel> userModelList, UserRepository userRepository) {
        this.userModelList = userModelList;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        this.userRepository.saveAll(userModelList);
    }
}
