package edu.nju.fyj.imchat.service.response.impl;

import edu.nju.fyj.imchat.connection.ClientSession;
import edu.nju.fyj.imchat.constant.PacketType;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.service.message.PacketService;
import edu.nju.fyj.imchat.service.message.impl.PacketServiceImpl;
import edu.nju.fyj.imchat.service.response.ResponseService;
import io.netty.channel.Channel;

/**
 * Created by yajfang on 2017/9/25.
 */
public class ResponseServiceImpl implements ResponseService {
    private PacketService packetService = new PacketServiceImpl();

    public void sendLoginSuccess(ClientSession session) {
        String uid = session.getUid();
        String token = session.getToken();

        Packet packet = packetService.buildTokenResponsePacket(token);

        Channel channel = session.getChannel();
        channel.writeAndFlush(packet);
    }

    public void sendLoginError(Channel channel) {
        Packet packet = packetService.buildResponsePacket(PacketType.LOGIN_FAIL,"");
        channel.writeAndFlush(packet);
    }

    public void sendAuthError(Channel channel) {
        Packet packet = packetService.buildResponsePacket(PacketType.TOKEN_ERROR,"");
        channel.writeAndFlush(packet);
    }

    public void sendHeartbeat(Channel channel) {//todo

    }
}
