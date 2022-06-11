package com.example.codec;

import com.example.constants.ReqType;
import com.example.constants.RpcConstants;
import com.example.core.Header;
import com.example.core.RpcProtocol;
import com.example.core.RpcRequest;
import com.example.core.RpcResponse;
import com.example.serial.ISerializer;
import com.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 22:59
 */

public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()< RpcConstants.HEAD_TOTAL_LEN){
            return;
        }
        short magic = byteBuf.readShort();
        if (magic!=RpcConstants.MAGIC){
            throw new IllegalArgumentException("Illegal request parameter 'magic'"+magic);
        }

        byte serialType = byteBuf.readByte();

        byte reqType = byteBuf.readByte();

        long requestId = byteBuf.readLong();

        int msgLength = byteBuf.readInt();

        if (byteBuf.readableBytes()<msgLength){
            return;
        }

        byte[] content = new byte[msgLength];
        byteBuf.readBytes(content);


        Header h = Header.builder()
                .magic(magic)
                .serialType(serialType)
                .reqType(reqType)
                .length(msgLength).build();

        ISerializer serializer = SerializerManager.getSerializer(serialType);
        ReqType byCode = ReqType.getByCode(reqType);
        switch (byCode){
            case REQUEST:
                RpcRequest re = serializer.deserialize(content, RpcRequest.class);
                    list.add(RpcProtocol.builder()
                        .header(h)
                        .content(re).build());
                break;
            case RESPONSE:
                RpcResponse res = serializer.deserialize(content, RpcResponse.class);
                list.add(RpcProtocol.builder()
                        .header(h)
                        .content(res)
                        .build());
                break;
            case HEARTBEAT:
                break;
            default:
                break;
        }

    }
}
