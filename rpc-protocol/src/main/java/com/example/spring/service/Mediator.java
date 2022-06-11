package com.example.spring.service;

import com.example.core.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存发布service的对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 17:11
 */

public class Mediator {

    public static Map<String, BeanMethod> beanMethodMap = new ConcurrentHashMap<>();

    private volatile static Mediator instance = null;

    private Mediator() {

    }


    /**
     * 单例获取对象
     *
     * @return
     */
    public static Mediator getInstance() {
        if (instance == null) {
            synchronized (Mediator.class) {
                if (instance == null) {
                    instance = new Mediator();
                }
            }
        }
        return instance;
    }


    /**
     * 反射调用bean 的方法
     * @param request
     * @return
     */
    public Object processor(RpcRequest request) {
        String key = request.getClassName() + "." + request.getMethodName();
        BeanMethod beanMethod = beanMethodMap.get(key);
        if (null == beanMethod) {
            return null;
        }

        Object bean = beanMethod.getBean();
        Method method = beanMethod.getMethod();
        try {
            //反射调用
            return method.invoke(bean, request.getParams());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
