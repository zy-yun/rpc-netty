package com.example.loadbalance;

import java.util.List;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 22:28
 */

public interface ILoadBalance<T> {

    /**
     * 获取实例
     *
     * @param servers
     * @return
     */
    T select(List<T> servers);


}
