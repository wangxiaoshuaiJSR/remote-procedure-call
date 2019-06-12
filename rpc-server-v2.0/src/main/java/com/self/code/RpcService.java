package com.self.code;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/6/12.
 * 普通注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RpcService {
    Class<?> value(); //拿到服务的接口

    /**
     * 版本号
     */
    String version() default "";
}
