package com.sp.rpc.registry;

import com.sp.rpc.protocol.common.SpRpcFuture;
import com.sp.rpc.protocol.common.SpRpcRequestHolder;
import com.sp.rpc.protocol.constant.ProtocolConstant;
import com.sp.rpc.protocol.definition.MsgHeader;
import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.definition.SpRpcRequest;
import com.sp.rpc.protocol.definition.SpRpcResponse;
import com.sp.rpc.protocol.enums.MsgTypeEnum;
import com.sp.rpc.protocol.enums.SerializationTypeEnum;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * RPC调用代理类
 *
 * @author luchao Created in 6/29/22 1:17 AM
 */
public class RpcInvokerProxy implements InvocationHandler {

    private final String serviceVersion;

    private final long timeout;

    private final RegistryService registryService;

    public RpcInvokerProxy(String serviceVersion, long timeout, RegistryService registryService) {
        this.serviceVersion = serviceVersion;
        this.timeout = timeout;
        this.registryService = registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构造RPC自定义协议对象
        SpRpcProtocol<SpRpcRequest> protocol = new SpRpcProtocol<>();
        MsgHeader msgHeader = new MsgHeader();
        long requestId = SpRpcRequestHolder.REQUEST_ID_GEN.incrementAndGet();
        msgHeader.setMagic(ProtocolConstant.MAGIC);
        msgHeader.setVersion(ProtocolConstant.VERSION);
        msgHeader.setRequestId(requestId);
        msgHeader.setSerialization((byte) SerializationTypeEnum.HESSIAN.getSerializationType());
        msgHeader.setMsgType((byte) MsgTypeEnum.REQUEST.getMsgType());
        msgHeader.setStatus((byte) 0x1);
        protocol.setMsgHeader(msgHeader);

        SpRpcRequest request = new SpRpcRequest();
        request.setServiceVersion(this.serviceVersion);
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamTypes(method.getParameterTypes());
        request.setParams(args);
        protocol.setBody(request);

        RpcConsumer rpcConsumer = new RpcConsumer();
        SpRpcFuture<SpRpcResponse> future = new SpRpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()), timeout);
        SpRpcRequestHolder.REQUEST_MAP.put(requestId, future);

        //发起RPC远程调用
        rpcConsumer.sendRequest(protocol, this.registryService);

        //等待RPC调用返回结果
        return future.getPromise().get(future.getTimeout(), TimeUnit.MILLISECONDS).getData();
    }
}
