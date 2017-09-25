package edu.nju.fyj.imchatclient;

import edu.nju.fyj.imchat.codec.MessageDecoder;
import edu.nju.fyj.imchat.codec.MessageEncoder;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.service.message.PacketService;
import edu.nju.fyj.imchat.service.message.impl.PacketServiceImpl;
import edu.nju.fyj.imchatclient.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by fangyj on 2017/9/25.
 */
public class ImClient {
    private final String host;
    private final int port;

    public static String uid;
    public static String token;

    private PacketService packetService = new PacketServiceImpl();

    public ImClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                .addLast(new MessageDecoder(1024*1024, 4, 4, -8, 0))
                                .addLast(new MessageEncoder())
                                .addLast(new ClientHandler());
                        }

                    });
            //连接服务器
            Channel channel = bootstrap.connect(host, port).sync().channel();
            System.out.println("connect to sever...");

            Scanner in = new Scanner(System.in);
            while(true) {
                String command = in.nextLine();
                if(command.equals("Q"))
                    break;
                if(command.startsWith("login")) {
                    uid = in.next();
                    String pwd = in.next();
                    Packet packet = packetService.buildLoginRequestPacket(uid, pwd);
                    channel.writeAndFlush(packet);

                } else if(command.startsWith("msg")) {
                    String to = in.next();
                    String content = in.next();
                    Packet packet = packetService.buildPersonPacket(uid, to, token, content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new ImClient("localhost", 8080).run();
    }
}
