package com.example.loadbalance;

import com.example.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * 随机选择实现
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 22:40
 */
public class RandomLoadBalance extends AbstractLoadBalance {


    @Override
    protected ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers) {
        int size = servers.size();
        Random random = new Random();
        return servers.get(random.nextInt(size));
    }
}
