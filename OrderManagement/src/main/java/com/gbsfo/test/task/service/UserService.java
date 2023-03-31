package com.gbsfo.test.task.service;


import com.gbsfo.test.task.entity.User;

public interface UserService extends DefaultService<User>{

    User createUser(User user);

    User findByUsername(String username);

}
