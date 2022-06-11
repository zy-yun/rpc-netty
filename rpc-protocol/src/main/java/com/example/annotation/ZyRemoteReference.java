package com.example.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 引用服务注解
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 16:46
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Autowired
public @interface ZyRemoteReference {
}
