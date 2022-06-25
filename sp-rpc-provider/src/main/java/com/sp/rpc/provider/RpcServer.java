package com.sp.rpc.provider;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * RPC服务启动器
 *
 * @author luchao Created in 6/22/22 1:21 AM
 */
@Slf4j
public class RpcServer {
    /**
     * RPC服务地址
     */
    private String serverAddr;

    /**
     * RPC服务端口
     */
    private Integer serverPort;

    private void startRpcServer() throws Exception {
        this.serverAddr = InetAddress.getLocalHost().getHostAddress();

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workder = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, workder)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = bootstrap.bind(this.serverAddr, this.serverPort).sync();
            log.info("serverAddr {} started on port{}", this.serverAddr, this.serverPort);
            channelFuture.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            workder.shutdownGracefully();
        }
    }

}
