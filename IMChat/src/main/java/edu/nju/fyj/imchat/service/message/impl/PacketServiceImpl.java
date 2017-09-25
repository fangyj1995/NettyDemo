package edu.nju.fyj.imchat.service.message.impl;

import edu.nju.fyj.imchat.constant.Constants;
import edu.nju.fyj.imchat.constant.PacketType;
import edu.nju.fyj.imchat.entity.Header;
import edu.nju.fyj.imchat.entity.body.Login;
import edu.nju.fyj.imchat.entity.body.Message;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.entity.body.Response;
import edu.nju.fyj.imchat.entity.body.TokenResponse;
import edu.nju.fyj.imchat.service.message.PacketService;

/**
 * Created by yajfang on 2017/9/25.
 */
public class PacketServiceImpl implements PacketService {
    public Packet buildGroupPacket(String sender, String receiver, String token, String content) {//todo
        return null;
    }

    public Packet buildPersonPacket(String sender, String receiver, String token, String content) {
        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setToken(token);
        msg.setContent(content);

        Header header = new Header();
        header.setCrcCode(Constants.MAGIC);
        header.setType(PacketType.PERSON_MSG);

        Packet packet = new Packet();
        packet.setHeader(header);
        packet.setBody(msg);
        return packet;
    }

    public Packet buildLoginRequestPacket(String uid, String pwd) {
        Login login = new Login();
        login.setUid(uid);
        login.setPwd(pwd);

        Header header = new Header();
        header.setCrcCode(Constants.MAGIC);
        header.setType(PacketType.LOGIN_REQUEST);

        Packet packet = new Packet();
        packet.setHeader(header);
        packet.setBody(login);
        return packet;
    }

    public Packet buildTokenResponsePacket(String token) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);

        Header header = new Header();
        header.setCrcCode(Constants.MAGIC);
        header.setType(PacketType.LOGIN_OK);

        Packet packet = new Packet();
        packet.setHeader(header);
        packet.setBody(tokenResponse);

        return packet;
    }

    public Packet buildResponsePacket(byte responseType, String content) {
        Response response = new Response();
        response.setContent(content);

        Header header = new Header();
        header.setCrcCode(Constants.MAGIC);
        header.setType(responseType);

        Packet packet = new Packet();
        packet.setHeader(header);
        packet.setBody(response);

        return packet;
    }
}
