package com.sp.rpc.protocol.handler;

import com.sp.rpc.protocol.definition.MsgHeader;
import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.definition.SpRpcRequest;
import com.sp.rpc.protocol.definition.SpRpcResponse;
import com.sp.rpc.protocol.enums.MsgStatusEnum;
import com.sp.rpc.protocol.enums.MsgTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * RPC请求处理器
 *
 * @author luchao Created in 6/26/22 6:00 PM
 */
@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<SpRpcProtocol<SpRpcRequest>> {
    private final Map<String, Object> rpcServiceMap;

    public RpcRequestHandler(Map<String, Object> rpcServiceMap){
        this.rpcServiceMap = rpcServiceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SpRpcProtocol<SpRpcRequest> protocol) throws Exception {
       RpcRequestProcessor.submitRequest(() -> {
           SpRpcProtocol<SpRpcResponse> resProtocol = new SpRpcProtocol<>();
           SpRpcResponse response = new SpRpcResponse();
           MsgHeader header = protocol.getMsgHeader();
           header.setMsgType((byte) MsgTypeEnum.RESPONSE.getMsgType());

           try {
               //调用RPC服务
               Object result = handle(protocol.getBody());
               response.setData(result);

               header.setStatus((byte) MsgStatusEnum.SUCCESS.getCode());
               resProtocol.setMsgHeader(header);
               resProtocol.setBody(response);
           }catch (Throwable e){
               header.setStatus((byte)MsgStatusEnum.FAILED.getCode());
               response.setMessage(e.toString());
               log.error("Process request error", header.getRequestId(), e);
           }

           //将数据写回服务消费者
           ctx.writeAndFlush(resProtocol);
       });
    }

    private Object handle(SpRpcRequest request) {
        //TODO 实现RPC请求
        return null;
    }
}
