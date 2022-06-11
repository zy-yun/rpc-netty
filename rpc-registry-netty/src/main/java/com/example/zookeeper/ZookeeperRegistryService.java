package com.example.zookeeper;

import com.example.IRegistryService;
import com.example.ServiceInfo;
import com.example.constants.RegistryConstant;
import com.example.loadbalance.ILoadBalance;
import com.example.loadbalance.RandomLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.List;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 22:00
 */
@Slf4j
public class ZookeeperRegistryService implements IRegistryService {

    /**
     * curator中服务注册与发现
     */
    private final ServiceDiscovery<ServiceInfo> serviceDiscovery;


    private ILoadBalance<ServiceInstance<ServiceInfo>> loadBalance;


    /**
     * 构造方法
     *
     * @param serviceAddress
     * @throws Exception
     */
    public ZookeeperRegistryService(String serviceAddress) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(serviceAddress,
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        //序列化方式
        JsonInstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .client(client)
                .serializer(serializer)
                .basePath(RegistryConstant.PATH)
                .build();
        this.serviceDiscovery.start();
        loadBalance = new RandomLoadBalance();
    }


    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {
        log.info("begin registry serviceInfo to Zookeeper server");
        ServiceInstance<ServiceInfo> instance = ServiceInstance.<ServiceInfo>builder()
                .address(serviceInfo.getServiceAddress())
                .port(serviceInfo.getServicePort())
                .name(serviceInfo.getServiceName())
                .payload(serviceInfo)
                .build();

        this.serviceDiscovery.registerService(instance);

    }


    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        Collection<ServiceInstance<ServiceInfo>> serviceInstances = this.serviceDiscovery.queryForInstances(serviceName);
        ServiceInstance<ServiceInfo> service = this.loadBalance.select((List<ServiceInstance<ServiceInfo>>) serviceInstances);
        if (null != service) {
            return service.getPayload();
        }
        return null;
    }
}
