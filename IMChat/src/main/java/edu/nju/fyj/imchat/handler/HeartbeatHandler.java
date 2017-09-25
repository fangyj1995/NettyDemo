package edu.nju.fyj.imchat.handler;

import edu.nju.fyj.imchat.entity.body.Body;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.entity.Header;
import edu.nju.fyj.imchat.constant.PacketType;
import edu.nju.fyj.imchat.service.response.ResponseService;
import edu.nju.fyj.imchat.service.response.impl.ResponseServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yajfang on 2017/9/25.
 */
public class HeartbeatHandler extends SimpleChannelInboundHandler<Packet> {

    private ResponseService responseService = new ResponseServiceImpl();

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        Header header = msg.getHeader();
        Body body = msg.getBody();
        if(header.getType() == PacketType.HEARTBEAT_REQUEST) {
            responseService.sendHeartbeat(ctx.channel());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
