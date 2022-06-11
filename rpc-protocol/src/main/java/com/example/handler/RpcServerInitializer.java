package com.example.handler;

import com.example.codec.RpcDecoder;
import com.example.codec.RpcEnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 通信服务端初始化对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 23:38
 */

public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0))
                .addLast(new RpcEnCoder())
                .addLast(new RpcDecoder())
                .addLast(new RpcServerHandler());

    }
}
