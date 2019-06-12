package com.self.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/6/12.
 */
@Configuration
@ComponentScan(basePackages = "com.self.code")
public class CommonConfig{
    @Bean(name = "rpcServer")
    public RpcServer getRpcServer(){
        return new RpcServer(8080);
    }

}
