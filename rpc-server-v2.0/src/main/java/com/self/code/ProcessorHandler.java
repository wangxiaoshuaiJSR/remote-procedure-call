package com.self.code;

import com.self.code.entity.RpcRequest;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/11.
 */
public class ProcessorHandler implements Runnable {
    private Socket socket;
    private Map<String,Object> handleMap;

    public ProcessorHandler(Socket socket, Map<String, Object> handleMap) {
        this.socket = socket;
        this.handleMap = handleMap;
    }

    @Override
    public void run() {
        ObjectOutputStream objectOutputStream=null;
        ObjectInputStream objectInputStream=null;
        try {
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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

    private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       String version = rpcRequest.getVersion();
       String serviceName = rpcRequest.getClassName();
       //增加版本控制
       if(!StringUtils.isEmpty(version)){
           serviceName=serviceName+"-"+version;
       }
        System.out.println(serviceName);
        System.out.println(handleMap);
       Object service = handleMap.get(serviceName);
       if(service==null){
           throw new RuntimeException("service not found"+serviceName);
       }
        Object[] args=rpcRequest.getParameters();
        Class<?> clazz=Class.forName( rpcRequest.getClassName());
        Method method=null;
        if(args!=null){
           Class<?>[] types = new Class[args.length];
           for (int i=0;i<types.length;i++) {
               types[i]=args[i].getClass();
           }
           method=clazz.getMethod(rpcRequest.getMethodName(),types);
       }else{
           method=clazz.getMethod(rpcRequest.getMethodName());
       }
       return method.invoke(service,args);//根据反射生成一个东西，就是这个样子的
    }
}
