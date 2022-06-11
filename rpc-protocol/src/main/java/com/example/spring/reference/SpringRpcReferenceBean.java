package com.example.spring.reference;

import com.example.IRegistryService;
import com.example.RegistryFactory;
import com.example.constants.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * rpc 引用bean，注入bean;动态生成工厂bean
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 17:50
 */

public class SpringRpcReferenceBean implements FactoryBean<Object> {


    private Object object;
//    private String serverAddress;
//    private int serverPort;
    private Class<?> interfaceClass;

    private String registryAddress;
    private byte registryType;



    public void init(){
        IRegistryService registryService = RegistryFactory.createRegistryService(registryAddress, RegistryType.getByCode(registryType));
        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                new RpcInvokerProxy(registryService));

    }


    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }


//    public void setServerAddress(String serverAddress) {
//        this.serverAddress = serverAddress;
//    }

//    public void setServerPort(int serverPort) {
//        this.serverPort = serverPort;
//    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRegistryType(byte registryType) {
        this.registryType = registryType;
    }
}
