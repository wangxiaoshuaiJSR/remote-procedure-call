package com.self.code;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/6/11.
 * 创建发布，用线程池，每个请求交给一个处理
 */
public class RpcProxyServer {

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(int port,Object service){
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            while (true){//不断的接收请求
                Socket socket = serverSocket.accept();//同步阻塞
                //每一个socket 交给一个processorHandler来处理
                executorService.execute(new ProcessorHandler(socket,service));
            }
        } catch (IOException e) {
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
}
