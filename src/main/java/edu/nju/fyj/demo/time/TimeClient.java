package edu.nju.fyj.demo.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by yajfang on 2017/9/20.
 */
public class TimeClient {
    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            /*
                If you specify only one EventLoopGroup, it will be used both as a boss group and as a worker group.
                The boss worker is not used for the client side though.
             */
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)Instead of NioServerSocketChannel, NioSocketChannel is being used to create a client-side Channel.
            /**
             * Note that we do not use childOption() here unlike we did with ServerBootstrap
             * because the client-side SocketChannel does not have a parent.
             */
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // Start the client.We should call the connect() method instead of the bind() method.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
