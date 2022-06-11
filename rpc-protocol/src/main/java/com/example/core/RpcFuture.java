package com.example.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * 客户端future，处理返回结果
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/5 18:39
 */
@Data
public class RpcFuture<T> {

    /**
     * 自带，会绑定一个事件轮询器
     */
    private Promise<T> promise;

    public RpcFuture(Promise<T> promise) {
        this.promise = promise;
    }



}
