package com.bookshop.daomicroservice.service;

import com.bookshop.daomicroservice.dao.User;
import com.bookshop.daomicroservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{


    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(){

    }

    @Override
    @StreamListener("input")
    public void saveUser(User user) {
        logger.info("Success recived = '{}'", user.getUsername());
        //userRepository.save(user);
    }
}
