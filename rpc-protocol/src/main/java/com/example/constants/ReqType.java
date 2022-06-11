package com.example.constants;

/**
 * 类型
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 21:57
 */

public enum ReqType {

    /**
     * 请求类型
     */
    REQUEST((byte)1),
    /**
     * 响应类型
     */
    RESPONSE((byte)2),
    /**
     * 心跳类型
     */
    HEARTBEAT((byte)3)


    ;

    private byte code;

    ReqType(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }


    public static ReqType getByCode(byte code){
        for (ReqType reqType:ReqType.values()
             ) {
            if (reqType.code==code){
                return reqType;
            }
        }
        return null;
    }
}
