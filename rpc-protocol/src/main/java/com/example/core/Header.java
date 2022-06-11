package com.example.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/1 23:42
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Header implements Serializable {

    /**
     * 魔数
     */
    private short magic;

    /**
     * 序列化 类型
     */
    private byte serialType;

    /**
     * 请求类型
     */
    private byte reqType;

    /**
     * 请求消息ID
     */
    private long requestId;

    /**
     * 消息长度
     */
    private int length;
}
