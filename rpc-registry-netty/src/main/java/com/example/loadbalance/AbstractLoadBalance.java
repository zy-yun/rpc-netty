package com.example.loadbalance;

import com.example.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * 负载均衡模板
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 22:31
 */

public abstract class AbstractLoadBalance implements ILoadBalance<ServiceInstance<ServiceInfo>> {


    @Override
    public ServiceInstance<ServiceInfo> select(List<ServiceInstance<ServiceInfo>> servers) {
        if (servers==null||servers.size()==0){
            return null;
        }
        if (servers.size()==1){
            return servers.get(0);
        }

        return doSelect(servers);
    }

    /**
     * 负载算法模板
     * @param servers
     * @return
     */
    protected abstract ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers);
}
