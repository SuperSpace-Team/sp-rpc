package com.sp.rpc.registry;

import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.definition.SpRpcRequest;
import com.sp.rpc.registry.model.ServiceMeta;
import com.sp.rpc.registry.utils.RpcServiceHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC消费端
 *
 * @author luchao Created in 6/29/22 1:28 AM
 */
@Slf4j
public class RpcConsumer {
    Bootstrap bootstrap = new Bootstrap();
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    public RpcConsumer() {
    }

    public RpcConsumer(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
        this.bootstrap = bootstrap;
        this.eventLoopGroup = eventLoopGroup;
    }

    public void sendRequest(SpRpcProtocol<SpRpcRequest> protocol, RegistryService registryService) throws Exception{
        SpRpcRequest request = protocol.getBody();
        Object[] params = request.getParams();
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());

        int invokeHashCode = params.length > 0 ? params[0].hashCode() : serviceKey.hashCode();
        ServiceMeta serviceMeta = registryService.discovery(serviceKey, invokeHashCode);

        if(serviceMeta != null){
            ChannelFuture future = bootstrap.connect(serviceMeta.getServerAddr(), serviceMeta.getServerPort()).sync();
            future.addListener((ChannelFutureListener)arg0 -> {
                if(future.isSuccess()){
                    log.info("Connect RPC server {} on port {} succeed.", serviceMeta.getServerAddr(), serviceMeta.getServerPort());
                }else {
                    log.error("Connect RPC server {} on port {} failed.", serviceMeta.getServerAddr(), serviceMeta.getServerPort());
                    future.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();
                }
            });

            //将数据发到远程服务节点
            future.channel().writeAndFlush(protocol);
        }
    }
}
