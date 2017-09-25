package edu.nju.fyj.imchat.server;

import edu.nju.fyj.imchat.handler.AuthHandler;
import edu.nju.fyj.imchat.handler.MessageHandler;
import edu.nju.fyj.imchat.codec.MessageDecoder;
import edu.nju.fyj.imchat.codec.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by fangyj on 2017/9/25.
 */
public class ImServer {
    private final String host;
    private final int port;

    public ImServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        //用来接收进来的连接, 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //启动 NIO 服务的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                .addLast(new MessageDecoder(1024*1024, 4, 4, -8, 0))
                                .addLast(new MessageEncoder())
                                .addLast(new AuthHandler())
                                .addLast(new MessageHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("chat server started");

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("chat server closed");
        }
    }

    public static void main(String[] args) throws Exception {

        new ImServer("localhost", 8080).run();
    }
}
