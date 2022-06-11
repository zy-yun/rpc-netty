package com.example.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 协议通信对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 21:53
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RpcProtocol<T> implements Serializable {

    /**
     * 头部
     */
    private Header header;

    /**
     * body体，请求 or 响应
     */
    private T content;

}
