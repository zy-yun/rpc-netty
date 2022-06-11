package com.example;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 21:55
 */

public interface IRegistryService {

    /**
     * 注册
     */
    void register(ServiceInfo serviceInfo) throws Exception;

    /**
     * 获取发现列表
     * @param serviceName
     */
    ServiceInfo discovery(String serviceName) throws Exception;
}
