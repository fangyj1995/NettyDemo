package edu.nju.fyj.imchat.service.message;

import edu.nju.fyj.imchat.entity.Packet;

/**
 * Created by yajfang on 2017/9/25.
 */
public interface PacketService {
    public Packet buildPersonPacket(String sender, String receiver, String token, String content);

    public Packet buildGroupPacket(String sender, String receiver, String token, String content);

    public Packet buildLoginRequestPacket(String uid, String pwd);

    public Packet buildTokenResponsePacket(String token);

    public Packet buildResponsePacket(byte responseType, String content);
}
