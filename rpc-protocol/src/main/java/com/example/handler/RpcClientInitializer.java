package com.example.handler;

import com.example.codec.RpcDecoder;
import com.example.codec.RpcEnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端初始化
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/5 18:30
 */
@Slf4j
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("begin RpcClientInitializer.");
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                12,4,0,0))
                .addLast(new LoggingHandler())
                .addLast(new RpcEnCoder())
                .addLast(new RpcDecoder())
                .addLast(new RpcClientHandler());
    }
}
