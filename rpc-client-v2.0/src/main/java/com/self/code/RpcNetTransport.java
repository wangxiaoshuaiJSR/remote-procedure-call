package com.self.code;

import com.self.code.entity.RpcRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2019/6/11.
 */
public class RpcNetTransport {
    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest rpcRequest){
        Socket socket=null;
        ObjectOutputStream objectOutputStream=null;
        ObjectInputStream objectInputStream=null;
        Object result=null;
        try {
            socket=new Socket(host,port);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            result = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(socket!=null){
                    socket.close();
                }
                if(objectInputStream!=null){
                    objectInputStream.close();
                }
                if(objectOutputStream!=null){
                    objectOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}
