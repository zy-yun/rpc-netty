package com.example.spring.service;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 发布的bean对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 17:10
 */
@Data
public class BeanMethod {

    private Object bean;
    private Method method;
}
