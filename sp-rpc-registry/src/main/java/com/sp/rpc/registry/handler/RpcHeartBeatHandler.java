package com.sp.rpc.registry.handler;

import com.sp.rpc.registry.model.HeartBeatData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * [Add Description Here]
 *
 * @author luchao Created in 7/1/22 1:14 AM
 */
public class RpcHeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        doHeartBeatTask(ctx);
    }

    private void doHeartBeatTask(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                HeartBeatData heartBeatData = buildHeartBeatData();
                ctx.writeAndFlush(heartBeatData);
                doHeartBeatTask(ctx);
            }
        }, 10, TimeUnit.SECONDS);
    }

    private HeartBeatData buildHeartBeatData() {
        return null;
    }
}
