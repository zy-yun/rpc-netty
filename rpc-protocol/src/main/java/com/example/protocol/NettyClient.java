package com.example.protocol;

import com.example.IRegistryService;
import com.example.ServiceInfo;
import com.example.core.RpcProtocol;
import com.example.core.RpcRequest;
import com.example.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 通信客户端
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/5 18:25
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;

    private final EventLoopGroup client = new NioEventLoopGroup();

    /**
     * 通信客户端初始化
     *
     */
    public NettyClient() {
        log.info("begin init netty client!");

        bootstrap = new Bootstrap();
        bootstrap.group(client)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

//
//        this.serviceAddress = serviceAddress;
//        this.servicePort = servicePort;
    }

//    private String serviceAddress;
//    private int servicePort;


    /**
     * 客户端发送请求
     *
     * @param protocol
     */
    public void sendRequest(RpcProtocol<RpcRequest> protocol, IRegistryService registryService) throws Exception {
        ServiceInfo serviceInfo = registryService.discovery(protocol.getContent().getClassName());
        final ChannelFuture future = bootstrap.connect(serviceInfo.getServiceAddress(), serviceInfo.getServicePort()).sync();
        future.addListener(listener->{
            if (future.isSuccess()) {
                log.info("connect rpc server:{} success", serviceInfo.getServiceAddress());
            } else {
                log.error("connect rpc server:{} failed.", serviceInfo.getServiceAddress());
                future.cause().printStackTrace();
                client.shutdownGracefully();
            }
        });
        future.channel().writeAndFlush(protocol);
    }
}
