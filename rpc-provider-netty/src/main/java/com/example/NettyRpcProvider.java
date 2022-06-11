package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务提供者
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/1 23:26
 */
@ComponentScan(basePackages = {"com.example.spring.service", "com.example.service", "com.example.annotation"})
@SpringBootApplication
public class NettyRpcProvider {
    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProvider.class, args);
    }
}
