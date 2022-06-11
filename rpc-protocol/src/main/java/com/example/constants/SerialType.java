package com.example.constants;

/**
 * 序列化类型
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 21:57
 */

public enum SerialType {

    /**
     * 请求类型
     */
    JSON_SERIAL((byte)1),
    /**
     * 响应类型
     */
    JAVA_SERIAL((byte)2)


    ;

    private byte code;

    SerialType(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
}
