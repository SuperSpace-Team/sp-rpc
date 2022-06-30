package com.sp.rpc.protocol.handler;

import com.sp.rpc.protocol.definition.MsgHeader;
import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.definition.SpRpcRequest;
import com.sp.rpc.protocol.definition.SpRpcResponse;
import com.sp.rpc.protocol.enums.MsgStatusEnum;
import com.sp.rpc.protocol.enums.MsgTypeEnum;
import com.sp.rpc.registry.utils.RpcServiceHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * RPC请求处理器
 *
 * @author luchao Created in 6/26/22 6:00 PM
 */
@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<SpRpcProtocol<SpRpcRequest>> {
    /**
     * 存储服务提供者对外开放的接口
     */
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

    private Object handle(SpRpcRequest request) throws InvocationTargetException {
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());
        Object serviceBean = rpcServiceMap.get(serviceKey);

        if(serviceBean == null){
            throw new RuntimeException(String.format("Service not exist:%s:%s",
                    request.getClassName(), request.getMethodName()));
        }

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParamTypes();
        Object[] parameters = request.getParams();

        //CGLib FastClass机制-生成一个FastClass子类、子类为代理类生成一个索引, 通过索引定位到要调用的方法
        FastClass fastClass = FastClass.create(serviceClass);
        int methodIndex = fastClass.getIndex(methodName, parameterTypes);

        return fastClass.invoke(methodIndex, serviceBean, parameters);
    }
}
