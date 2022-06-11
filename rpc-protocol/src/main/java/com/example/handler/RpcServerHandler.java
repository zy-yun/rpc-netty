package com.example.handler;

import com.example.constants.ReqType;
import com.example.core.Header;
import com.example.core.RpcProtocol;
import com.example.core.RpcRequest;
import com.example.core.RpcResponse;
import com.example.spring.SpringBeanManager;
import com.example.spring.service.Mediator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 远程服务handler对象
 *
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/2 23:44
 */

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcRequest> rpcRequestRpcProtocol) throws Exception {

        RpcProtocol<RpcResponse> resProtocol = new RpcProtocol();
        Header header = rpcRequestRpcProtocol.getHeader();
        header.setReqType(ReqType.RESPONSE.getCode());
        //反射调用
        Object result = Mediator.getInstance().processor(rpcRequestRpcProtocol.getContent());
//        Object result = invoke(rpcRequestRpcProtocol.getContent());
        resProtocol.setHeader(header);
        resProtocol.setContent(RpcResponse.builder()
                .data(result)
                .msg("success")
                .build());
        channelHandlerContext.writeAndFlush(resProtocol);
    }


    /**
     * 代理执行目标方法
     *
     * @param request
     * @return
     */
    @Deprecated
    private Object invoke(RpcRequest request) {
        try {
            Class<?> clazz = Class.forName(request.getClassName());
            Object bean = SpringBeanManager.getBean(clazz);
            Method declaredMethod = clazz.getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
            return declaredMethod.invoke(bean, request.getParams());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;

    }
}
