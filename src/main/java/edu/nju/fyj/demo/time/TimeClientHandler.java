package edu.nju.fyj.demo.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.util.Date;

/**
 * Created by yajfang on 2017/9/20.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)In TCP/IP, Netty reads the data sent from a peer into a ByteBuf.
        /*
            It looks very simple and does not look any different from the server side example.
            However, this handler sometimes will refuse to work raising an IndexOutOfBoundsException.
            We discuss why this happens in the next section.
         */


        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
