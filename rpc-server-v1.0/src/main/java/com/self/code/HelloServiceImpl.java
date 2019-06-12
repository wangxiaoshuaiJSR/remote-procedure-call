package com.self.code;

import com.self.code.entity.IHelloService;
import com.self.code.entity.User;

/**
 * Created by Administrator on 2019/6/11.
 */
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String content) {
        System.out.println("request in sayHello:"+content);
        return "Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in saveUser:"+user);
        return "SUCCESS";
    }
}
