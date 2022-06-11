package com.example.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 通信请求对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 21:47
 */
@Data
public class RpcRequest implements Serializable {

    /**
     * 类名
     */
    private String className;
    /**
     * 目标方法
     */
    private String methodName;
    /**
     * 请求参数
     */
    private Object[] params;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
}
