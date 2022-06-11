package com.example.handler;

import com.example.core.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端处理器
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/5 18:51
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponse> rpcResponseRpcProtocol) throws Exception {
        log.info("receive rpc server result!");

        long requestId = rpcResponseRpcProtocol.getHeader().getRequestId();
        RpcFuture<RpcResponse> rpcFuture = RequestHolder.REQUEST_MAP.remove(requestId);

        //返回结果
        rpcFuture.getPromise().setSuccess(rpcResponseRpcProtocol.getContent());
    }
}
