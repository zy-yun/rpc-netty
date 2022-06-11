package com.example.spring.reference;

import com.example.IRegistryService;
import com.example.constants.ReqType;
import com.example.constants.RpcConstants;
import com.example.constants.SerialType;
import com.example.core.*;
import com.example.protocol.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理大H对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/5 18:09
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

//    private String host;
//    private int port;

    IRegistryService registryService;
    public RpcInvokerProxy(IRegistryService registryService) {
        this.registryService=registryService;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        log.info("begin invoke target server!");

        RpcProtocol<RpcRequest> protocol = new RpcProtocol<>();
        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        Header header = Header.builder()
                .magic(RpcConstants.MAGIC)
                .serialType(SerialType.JSON_SERIAL.getCode())
                .reqType(ReqType.REQUEST.getCode())
                .requestId(requestId)
                .length(0)
                .build();
        protocol.setHeader(header);

        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(objects);
        protocol.setContent(request);

        NettyClient nettyClient = new NettyClient();
        RpcFuture<RpcResponse> future = new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));

        RequestHolder.REQUEST_MAP.put(requestId, future);
        nettyClient.sendRequest(protocol,this.registryService);

        return future.getPromise().get().getData();
    }
}
