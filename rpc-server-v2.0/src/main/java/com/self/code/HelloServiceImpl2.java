package com.self.code;

import com.self.code.entity.IHelloService;
import com.self.code.entity.User;

/**
 * Created by Administrator on 2019/6/11.
 */
@RpcService(value = IHelloService.class,version = "V2.0")
public class HelloServiceImpl2 implements IHelloService {
    @Override
    public String sayHello(String content) {
        System.out.println("request in sayHello[V2.0]:"+content);
        return "Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("V2.0request in saveUser:"+user);
        return "SUCCESS";
    }
}
