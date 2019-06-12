package com.self.code;

import com.self.code.entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by Administrator on 2019/6/11.
 */
public class ProcessorHandler implements Runnable {
    private Socket socket;
    private Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectOutputStream objectOutputStream=null;
        ObjectInputStream objectInputStream=null;
        try {
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            //得到传入的参数，类，方法名称
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest) throws Exception {
        Object[] args = rpcRequest.getParameters();//所有的参数
        Class<?>[] types = new Class[args.length];//为得到方法做准备
        for (int i=0;i<args.length;i++) {
            types[i]=args[i].getClass();
        }
        Class clazz = Class.forName(rpcRequest.getClassName());
        Method method = clazz.getMethod(rpcRequest.getMethodName(),types);
        return method.invoke(service,args);//反射得到对应的东西
    }
}
