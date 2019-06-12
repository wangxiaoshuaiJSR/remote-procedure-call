package com.self.code;

import com.self.code.entity.IHelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Administrator on 2019/6/12.
 */
public class AppClient {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CommonConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"localhost",8080);
        iHelloService.sayHello("wxs");
    }
}
