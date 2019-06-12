package com.self.code;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Administrator on 2019/6/12.
 */
public class AppServer {
    public static void main(String[] args) {
        ApplicationContext context=new AnnotationConfigApplicationContext(CommonConfig.class);
        ((AnnotationConfigApplicationContext)context).start();
    }
}
