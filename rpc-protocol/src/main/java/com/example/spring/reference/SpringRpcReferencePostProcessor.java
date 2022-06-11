package com.example.spring.reference;

import com.example.annotation.ZyRemoteReference;
import com.example.spring.service.SpringRpcProviderBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 18:05
 */
@Slf4j
public class SpringRpcReferencePostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private ApplicationContext context;

    private ClassLoader classLoader;

    private RpcClientProperties clientProperties;


    private final Map<String, BeanDefinition> rpcReferenceBeanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 构造方法
     *
     * @param clientProperties
     */
    public SpringRpcReferencePostProcessor(RpcClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * spring容器加载了bean的定义文件之后，实例化bean之前执行，
     *
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        for (String beanDefinitionName:configurableListableBeanFactory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName!=null){
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName,this.classLoader);
                ReflectionUtils.doWithFields(clazz, this::parseRpcReference);

            }
        }


        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)configurableListableBeanFactory;
        this.rpcReferenceBeanDefinitionMap.forEach((beanName,definition)->{
            if (context.containsBean(beanName)){
                log.warn("SpringContext already register bean {}",beanName);
                return;
            }
            registry.registerBeanDefinition(beanName,definition);
            log.info("registered RpcReferenceBean {} success",beanName);
        });
    }


    private void parseRpcReference(Field field){
        ZyRemoteReference annotation = AnnotationUtils.getAnnotation(field, ZyRemoteReference.class);
        //不等于空时，需要实例化当前实例(工厂bean)
        if (annotation!=null){
            BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SpringRpcReferenceBean.class);
            definitionBuilder.setInitMethodName("init");
            definitionBuilder.addPropertyValue("interfaceClass",field.getType());
            definitionBuilder.addPropertyValue("serverAddress",clientProperties.getServerAddress());
            definitionBuilder.addPropertyValue("serverPort",clientProperties.getServerPort());
            definitionBuilder.addPropertyValue("registryAddress",clientProperties.getRegistryAddress());
            definitionBuilder.addPropertyValue("registryType",clientProperties.getRegistryType());
            AbstractBeanDefinition beanDefinition1 = definitionBuilder.getBeanDefinition();
            rpcReferenceBeanDefinitionMap.put(field.getName(),beanDefinition1);
        }
    }

}
