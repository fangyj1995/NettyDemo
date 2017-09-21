package demo.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by yajfang on 2017/9/20.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {//当接受到消息时被调用
        ByteBuf in = (ByteBuf) msg;

        ctx.write(msg);
        /**
         * Please note that we did not release the received message unlike we did in the DISCARD example.
         * It is because Netty releases it for you when it is written out to the wire.
         *
         * ctx.write(Object) does not make the message written out to the wire.
         * It is buffered internally, and then flushed out to the wire by ctx.flush().
         * Alternatively, you could call ctx.writeAndFlush(msg) for brevity.
         */
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {//当处理事件时发生异常或IO错误时被调用
        //close the connection when an exception is raised
        cause.printStackTrace();
        ctx.close();
    }
}
