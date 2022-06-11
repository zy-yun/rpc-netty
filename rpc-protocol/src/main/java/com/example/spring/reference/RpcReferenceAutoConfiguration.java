package com.example.spring.reference;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 19:47
 */
@Configuration
public class RpcReferenceAutoConfiguration implements EnvironmentAware{

    private Environment environment;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public SpringRpcReferencePostProcessor springRpcReferencePostProcessor(){
        return new SpringRpcReferencePostProcessor(RpcClientProperties.builder()
//                .serverAddress(this.environment.getProperty("zy.client.serverAddress"))
//                .serverPort(Integer.valueOf(this.environment.getProperty("zy.client.serverPort")))
                .registryAddress(this.environment.getProperty("zy.client.registryAddress"))
                .registryType(Byte.parseByte(this.environment.getProperty("zy.client.registryType")))
                .build());
    }


}
