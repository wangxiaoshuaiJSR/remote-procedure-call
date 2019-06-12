package com.self.code.entity;

public interface IHelloService {

    //
    String sayHello(String content);

    /**
     * 保存用户
     * @param user
     * @return
     */
    String saveUser(User user);

}