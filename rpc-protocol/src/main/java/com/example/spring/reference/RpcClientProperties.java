package com.example.spring.reference;

import lombok.Builder;
import lombok.Data;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 19:33
 */
@Builder
@Data
public class RpcClientProperties {
    private String serverAddress = "127.0.0.1";
    private int serverPort = 20880;

    private String registryAddress;
    private byte registryType;
}
