package com.self.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/6/12.
 */
@Configuration
public class CommonConfig {
    @Bean(name="rpcProxyClient")
    public RpcProxyClient getRpcProxyClient(){
        return new RpcProxyClient();
    }
}
