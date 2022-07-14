package com.example.spring4resourceserver.batch;

import com.example.spring4resourceserver.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;



@Slf4j
public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {
        log.info("Reading user  >>>>>>>>>>>>. {}", user);
        return user;
    }

}
