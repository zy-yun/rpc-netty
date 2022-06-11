package com.example.spring.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 17:28
 */
@Data
@ConfigurationProperties(prefix = "zy.rpc")
public class RpcServerProperties {

    private String serverAddress;

    private String registryAddress;


    private int serverPort;

    private byte registryType;
}
