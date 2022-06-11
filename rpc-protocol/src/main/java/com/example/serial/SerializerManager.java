package com.example.serial;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化管理
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 22:55
 */

public class SerializerManager {

    private final static ConcurrentHashMap<Byte, ISerializer> serializer = new ConcurrentHashMap<>();

    static {
        ISerializer json = new JsonSerializer();
        ISerializer java = new JavaSerializer();
        serializer.put(json.getType(), json);
        serializer.put(java.getType(), java);
    }


    /**
     * 获取序列化对象
     *
     * @param key
     * @return
     */
    public static ISerializer getSerializer(byte key) {
        ISerializer iSerializer = serializer.get(key);
        if (null == iSerializer) {
            return new JavaSerializer();
        }

        return iSerializer;
    }
}
