package edu.nju.fyj.imchatclient.handler;

import edu.nju.fyj.imchat.constant.PacketType;
import edu.nju.fyj.imchat.entity.Header;
import edu.nju.fyj.imchat.entity.body.Message;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.entity.body.TokenResponse;
import edu.nju.fyj.imchatclient.ImClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by fangyj on 2017/9/25.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Packet> {

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        Header header = msg.getHeader();
        if(header.getType() == PacketType.LOGIN_OK) {
            System.out.println(ImClient.uid+ "[you] login success");
            TokenResponse tokenResponse = (TokenResponse) msg.getBody();
            String token = tokenResponse.getToken();

            ImClient.token = token;
            System.out.println("token: " + token);

        } else if(header.getType() == PacketType.LOGIN_FAIL) {
            System.out.println(ImClient.uid+ "[you] login fail");
        } else if(header.getType() == PacketType.PERSON_MSG) {
            Message message = (Message) msg.getBody();
            String from = message.getSender();
            System.out.println(ImClient.uid+ "[you] received msg:\n" + msg.getBody() + " from " + from);
        } else if(header.getType() == PacketType.TOKEN_ERROR) {
            System.out.println("token error , message not send");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
