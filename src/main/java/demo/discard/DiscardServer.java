package demo.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yajfang on 2017/9/20.
 */

/**
 * NioEventLoopGroup is a multithreaded event loop that handles I/O operation.
 * NioEventLoopGroup是处理IO操作的多线程事件循环
 * Netty provides various EventLoopGroup implementations for different kind of transports.
 * We are implementing a server-side application in this example, and therefore two NioEventLoopGroup will be used.
 *
 * The first one, often called 'boss', accepts an incoming connection.
 * boss负责处理到来的连接
 * The second one, often called 'worker', handles the traffic of the accepted connection once the boss accepts the connection and registers the accepted connection to the worker.
 * worker处理被boss接受的连接，Boss接受连接以后就会注册给worker
 * How many Threads are used and how they are mapped to the created Channels depends on the EventLoopGroup implementation and may be even configurable via a constructor.
 * 使用多少线程以及他们与创建的通道之间的映射关系取决于EventLoopGroup的实现，并且可以通过构造函数配置
 *
 * ServerBootstrap is a helper class that sets up a server. You can set up the server using a Channel directly.
 * However, please note that this is a tedious process, and you do not need to do that in most cases.
 *
 * Here, we specify to use the NioServerSocketChannel class which is used to instantiate a new Channel to accept incoming connections.
 * 指定NioServerSocketChannel.class用来实例化处理到来的连接的channel
 *
 * The handler specified here will always be evaluated by a newly accepted Channel.
 * The ChannelInitializer is a special handler that is purposed to help a user configure a new Channel.
 * ChannelInitializer是用来帮助用户配置新Channel的特殊的handler
 * It is most likely that you want to configure the ChannelPipeline of the new Channel by adding some handlers such as EchoServerHandler to implement your network application.
 * 我们可以通过添加handler的方式来配置channel的ChannelPipeline
 * As the application gets complicated, it is likely that you will add more handlers to the pipeline and extract this anonymous class into a top level class eventually.
 *
 * Did you notice option() and childOption()?
 * option() is for the NioServerSocketChannel that accepts incoming connections.
 * option配置处理到来的连接的channel
 * childOption() is for the Channels accepted by the parent ServerChannel, which is NioServerSocketChannel in this case.
 * childOption配置被父ServerChannel接受的channels
 *
 * We are ready to go now.
 * What's left is to bind to the port and to start the server.
 * Here, we bind to the port 8080 of all NICs (network interface cards) in the machine.
 * You can now call the bind() method as many times as you want (with different bind addresses.)
 */
public class DiscardServer {
    private int port;
    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            /*
                public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)

                Set the EventLoopGroup for the parent (acceptor) and the child (client).
                These EventLoopGroup's are used to handle all the events and IO for ServerChannel and Channel's.
             */
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());//Inserts ChannelHandlers at the last position of this pipeline.
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //Bind and start to accept incoming connections绑定端口，开始接收到来的连接请求
            ChannelFuture f = b.bind(port).sync();
            System.out.println("bind port " + port +"....");
            //Wait until the server socket is closed
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * you could enter telnet localhost 8080 in the command line and type something.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port;
        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}
