package edu.nju.fyj.imchat.handler;

import edu.nju.fyj.imchat.connection.ClientSession;
import edu.nju.fyj.imchat.connection.SessionManager;
import edu.nju.fyj.imchat.entity.body.Message;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.entity.Header;
import edu.nju.fyj.imchat.constant.PacketType;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yajfang on 2017/9/25.
 */
public class MessageHandler extends SimpleChannelInboundHandler<Packet> {

    private SessionManager sessionManager = SessionManager.getInstance();

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        Header header = msg.getHeader();
        if(header.getType() == PacketType.PERSON_MSG) {
            Message body = (Message)msg.getBody();
            ClientSession to = sessionManager.getSession(body.getReceiver());

            if(to != null) {
                body.setToken(null);
                ChannelFuture future = to.getChannel().writeAndFlush(msg);

            } else {//todo

            }
        } else if(header.getType() == PacketType.GROUP_MSG) {

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
