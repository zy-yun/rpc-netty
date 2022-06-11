package com.example.spring.service;

import com.example.IRegistryService;
import com.example.RegistryFactory;
import com.example.constants.RegistryType;
import com.example.zookeeper.ZookeeperRegistryService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 17:31
 */
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties){
        IRegistryService registryService = RegistryFactory.createRegistryService(rpcServerProperties.getRegistryAddress(), RegistryType.getByCode(rpcServerProperties.getRegistryType()));
        return new SpringRpcProviderBean(rpcServerProperties.getServerPort(),rpcServerProperties.getServerAddress(),registryService);
    }
}
