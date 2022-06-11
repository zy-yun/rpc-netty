package com.example.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通信响应对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 21:51
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RpcResponse implements Serializable {
    private Object data;
    private String msg;
}
