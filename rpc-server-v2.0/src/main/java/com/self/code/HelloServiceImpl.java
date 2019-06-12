package com.self.code;

import com.self.code.entity.IHelloService;
import com.self.code.entity.User;

/**
 * Created by Administrator on 2019/6/11.
 */
@RpcService(value = IHelloService.class,version = "V1.0")
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String content) {
        System.out.println("request in sayHello[V1.0]:"+content);
        return "Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("V1.0request in saveUser:"+user);
        return "SUCCESS";
    }
}
