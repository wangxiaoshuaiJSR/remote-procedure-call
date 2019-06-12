package com.self.code;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2019/6/11.
 */
public class RpcProxyClient {

    public <T> T clientProxy(final Class<T> interfac,final String host,final int port){
        return (T) Proxy.newProxyInstance(interfac.getClassLoader(),new Class[]{interfac},
                new RemoteInvocationHandler(host,port));
    }

}
