package com.example.serial;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 22:31
 */

public interface ISerializer {

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data,Class<T> clazz);

    /**
     * 获取序列化的类型
     *
     * @return
     */
    byte getType();
}
