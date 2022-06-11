package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 19:55
 */
@ComponentScan(basePackages = {"com.example.spring.reference", "com.example.controller", "com.example.annotation"})
@SpringBootApplication
public class NettyConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(NettyConsumerMain.class, args);
    }
}
