package com.example.spring.service;

import com.example.IRegistryService;
import com.example.ServiceInfo;
import com.example.annotation.ZyRemoteService;
import com.example.protocol.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 16:53
 */
@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {

    private final int serverPort;
    private final String serverAddress;

    private final IRegistryService registryService;

    public SpringRpcProviderBean(int serverPort, String serverAddress, IRegistryService registryService) {
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
        this.registryService = registryService;
    }

    /**
     * bean实例化的时候执行的方法
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin deploy Netty Server to host {} on port {}",this.serverAddress,this.serverPort);
        new Thread(()->{
            new NettyServer(this.serverAddress,this.serverPort).startNettyServer();
        }).start();
    }


    /**
     * bean实例化之后执行的方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(ZyRemoteService.class)){
            //获取当前bean的所有方法
            Method[] declaredMethods = bean.getClass().getDeclaredMethods();
            for (Method method:declaredMethods){
                String serviceName = bean.getClass().getInterfaces()[0].getName();
                String key = serviceName+"."+method.getName();

                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);

                Mediator.beanMethodMap.put(key,beanMethod);

                //服务注册
                try {
                    registryService.register(ServiceInfo.builder()
                            .serviceAddress(this.serverAddress)
                            .servicePort(this.serverPort)
                            .serviceName(serviceName).build());
                } catch (Exception e) {
                    log.error("register service {} failed {}",serviceName,e);
                }
            }
        }


        return bean;
    }
}
