package com.example;

import com.example.constants.RegistryType;
import com.example.zookeeper.ZookeeperRegistryService;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 22:54
 */

public class RegistryFactory {

    public static IRegistryService createRegistryService(String address, RegistryType registryType) {
        IRegistryService registryService = null;
        try {
            switch (registryType){
                case ZOOKEEPER:
                    registryService = new ZookeeperRegistryService(address);
                    break;
                case EUREKA:
                    //todo
                    break;
                case NACOS:
                    //todo
                    break;
                default:
                    registryService = new ZookeeperRegistryService(address);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return registryService;
    }
}
