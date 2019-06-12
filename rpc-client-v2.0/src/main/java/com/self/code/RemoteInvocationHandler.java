package com.self.code;

import com.self.code.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/6/11.
 */
public class RemoteInvocationHandler implements InvocationHandler {
    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest=new RpcRequest();
        rpcRequest.setVersion("V1.0");
        rpcRequest.setParameters(args);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        RpcNetTransport rpcNetTransport=new RpcNetTransport(host,port);
        return rpcNetTransport.send(rpcRequest);
    }
}
