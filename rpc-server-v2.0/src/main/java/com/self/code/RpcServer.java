package com.self.code;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/6/12.
 */
@Component
public class RpcServer implements ApplicationContextAware,InitializingBean {
    private Map<String,Object> handleMap=new HashMap<>();
    ExecutorService executorService= Executors.newCachedThreadPool();
    private int port;

    public RpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            while (true){
                Socket socket=serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,handleMap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> objectMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(objectMap!=null){
            for (Object object : objectMap.values()) {
                RpcService rpcService = object.getClass().getAnnotation(RpcService.class);
                String serviceName=rpcService.value().getName();//拿到接口定义
                String version=rpcService.version();//版本号
                if(!StringUtils.isEmpty(version)){
                    serviceName=serviceName+"-"+version;
                }
                handleMap.put(serviceName,object);
            }
        }
    }
}
