package edu.nju.fyj.imchat.handler;

import edu.nju.fyj.imchat.entity.Body;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.protocol.Header;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yajfang on 2017/9/25.
 */
public class MessageHandler extends SimpleChannelInboundHandler<Packet> {

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        Header header = msg.getHeader();
        Body body = msg.getBody();


    }
}
