package com.example.codec;

import com.example.core.Header;
import com.example.core.RpcProtocol;
import com.example.serial.ISerializer;
import com.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 23:23
 */

public class RpcEnCoder extends MessageToByteEncoder<RpcProtocol<Object>> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol<Object> objectRpcProtocol, ByteBuf byteBuf) throws Exception {

        Header header = objectRpcProtocol.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getSerialType());
        byteBuf.writeByte(header.getReqType());
        byteBuf.writeLong(header.getRequestId());

        //获取序列化方式
        ISerializer serializer = SerializerManager.getSerializer(header.getSerialType());
        byte[] data = serializer.serialize(objectRpcProtocol.getContent());
        //设置报文长度
        byteBuf.writeInt(data.length);
        //写入报文
        byteBuf.writeBytes(data);

    }
}
