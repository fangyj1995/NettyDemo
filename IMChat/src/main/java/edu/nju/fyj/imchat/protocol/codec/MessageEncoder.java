package edu.nju.fyj.imchat.protocol.codec;

import edu.nju.fyj.imchat.protocol.Message;
import edu.nju.fyj.imchat.serialization.marshall.MarshallingCodeCFactory;
import edu.nju.fyj.imchat.serialization.marshall.NettyMarshallingEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by fangyj on 2017/9/24.
 */
public class MessageEncoder extends MessageToMessageEncoder<Message>{

    NettyMarshallingEncoder marshallingEncoder;

    public MessageEncoder() throws IOException {
        this.marshallingEncoder = MarshallingCodeCFactory.buildMarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        if(msg == null || msg.getHeader() == null)
            throw new IllegalArgumentException("encode msg is null");

        ByteBuf sendBuf = Unpooled.buffer();
        //写入校验位
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        //写入长度
        sendBuf.writeInt(msg.getHeader().getLength());
        //写入消息类型
        sendBuf.writeInt(msg.getHeader().getType());
        //写入附件个数
        sendBuf.writeInt(msg.getHeader().getAttachment().size());

        String key = null;
        byte[] keyBytes = null;
        Object value = null;
        for(Map.Entry<String, Object> param: msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyBytes = key.getBytes("UTF-8");
            sendBuf.writeInt(keyBytes.length);
            sendBuf.writeBytes(keyBytes);

            value = param.getValue();

            marshallingEncoder.encode(ctx, value, sendBuf);
        }

        key = null;
        keyBytes = null;
        value = null;

        if(msg.getBody() != null) {
            marshallingEncoder.encode(ctx, msg.getBody(), sendBuf);
        } else sendBuf.writeInt(0);

        int readableBytes = sendBuf.readableBytes();

        sendBuf.setInt(4, readableBytes);

        //把msg传给下一个handler
        out.add(sendBuf);
    }
}
