package com.example.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 请求对象，处理请求ID、及对应的future
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/5 18:21
 */

public class RequestHolder {
    public static final AtomicLong REQUEST_ID = new AtomicLong();

    public static final Map<Long,RpcFuture> REQUEST_MAP = new ConcurrentHashMap<>();
}
