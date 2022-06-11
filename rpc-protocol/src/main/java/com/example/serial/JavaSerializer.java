package com.example.serial;

import com.example.constants.SerialType;

import java.io.*;

/**
 * java序列化对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 22:44
 */

public class JavaSerializer implements ISerializer {


    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte getType() {
        return SerialType.JAVA_SERIAL.getCode();
    }
}
