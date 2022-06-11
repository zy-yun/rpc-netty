package com.example.constants;

/**
 * 注册中心类型
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 22:48
 */

public enum RegistryType {
    /**
     * zookeeper注册中心
     */
    ZOOKEEPER((byte)0),
    /**
     * eureka注册中心
     */
    EUREKA((byte)1),
    /**
     * nacos注册中心
     */
    NACOS((byte)2)

    ;

    private byte code;

    RegistryType(byte code){
        this.code = code;
    }

    public static RegistryType getByCode(byte code){
        for (RegistryType reqType:RegistryType.values()
        ) {
            if (reqType.code==code){
                return reqType;
            }
        }
        return null;
    }
}
