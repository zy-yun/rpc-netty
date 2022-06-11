package com.example.serial;

import com.alibaba.fastjson.JSON;
import com.example.constants.SerialType;

/**
 * json序列化对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 22:48
 */

public class JsonSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data), clazz);
    }

    @Override
    public byte getType() {
        return SerialType.JSON_SERIAL.getCode();
    }
}
