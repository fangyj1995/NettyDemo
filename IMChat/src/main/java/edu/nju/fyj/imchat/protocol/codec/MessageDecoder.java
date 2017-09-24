package edu.nju.fyj.imchat.protocol.codec;

import edu.nju.fyj.imchat.protocol.Header;
import edu.nju.fyj.imchat.protocol.Message;
import edu.nju.fyj.imchat.serialization.marshall.MarshallingCodeCFactory;
import edu.nju.fyj.imchat.serialization.marshall.NettyMarshallingDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangyj on 2017/9/24.
 */

/**
 * LengthFieldBasedFrameDecoder支持自动的tcp粘包和半包处理，
 * 只需要给出标识长度的字段偏移量和消息长度自身所占的字节数
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder{
    private NettyMarshallingDecoder marshallingDecoder;

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.marshallingDecoder = MarshallingCodeCFactory.buildMarshallingDecoder();
    }

    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //调用父类的解码方法后， 返回的就是整包消息或者为空， 如果为空说明是个半包消息， 直接返回
        //继续由Io线程读取后续的码流
        ByteBuf frame = (ByteBuf)super.decode(ctx, in);

        if(frame == null)
            return null;

        Message msg = new Message();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());

        int size = frame.readInt();
        if(size > 0) {
            Map<String, Object> attach = new HashMap<String, Object>();
            int keySize = 0;
            byte[] keyBytes = null;
            String key = null;
            for(int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyBytes = new byte[keySize];
                in.readBytes(keyBytes);
                key = new String(keyBytes, "UTF-8");
                attach.put(key, marshallingDecoder.decode(ctx, frame));
            }
            key = null;
            keyBytes = null;
            header.setAttachment(attach);
        }

        //decode body
        if(frame.readableBytes() > 0) {
            msg.setBody(marshallingDecoder.decode(ctx, frame));
        }
        msg.setHeader(header);
        return msg;
    }
}
