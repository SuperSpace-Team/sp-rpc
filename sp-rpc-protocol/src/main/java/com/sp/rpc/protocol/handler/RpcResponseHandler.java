package com.sp.rpc.protocol.handler;

import com.sp.rpc.protocol.common.SpRpcFuture;
import com.sp.rpc.protocol.common.SpRpcRequestHolder;
import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.definition.SpRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * RPC响应处理器
 * 处理不同线程的响应结果
 *
 * @author luchao Created in 6/26/22 6:20 PM
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<SpRpcProtocol<SpRpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SpRpcProtocol<SpRpcResponse> msg) throws Exception {
        long requestId = msg.getMsgHeader().getRequestId();
        SpRpcFuture<SpRpcResponse> future = SpRpcRequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getBody());
    }
}
