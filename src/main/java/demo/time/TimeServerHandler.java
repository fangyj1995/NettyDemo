package demo.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by yajfang on 2017/9/20.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * As explained, the channelActive() method will be invoked when a connection is established and ready to generate traffic.
     * 当连接建立时被调用
     * Let's write a 32-bit integer that represents the current demo.time in this method.
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        /**
         * To send a new message, we need to allocate a new buffer which will contain the message.
         * We are going to write a 32-bit integer, and therefore we need a ByteBuf whose capacity is at least 4 bytes.
         * Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer.
         */
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        /**
         * But wait, where's the flip? Didn't we used to call java.nio.ByteBuffer.flip() before sending a message in NIO?
         * ByteBuf does not have such a method because it has two pointers; one for read operations and the other for write operations.
         * The writer index increases when you write something to a ByteBuf while the reader index does not change.
         * The reader index and the writer index represents where the message starts and ends respectively.
         * In contrast, NIO buffer does not provide a clean way to figure out where the message content starts and ends without calling the flip method.
         * You will be in trouble when you forget to flip the buffer because nothing or incorrect data will be sent.
         * Such an error does not happen in Netty because we have different pointer for different operation types.
         * You will find it makes your life much easier as you get used to it -- a life without flipping out!
         */
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)ChannelFuture代表一次还未发生的IO操作，异步

        /**
         * How do we get notified when a write request is finished then?
         * This is as simple as adding a ChannelFutureListener to the returned ChannelFuture.
         * Here, we created a new anonymous ChannelFutureListener which closes the Channel when the operation is done.
         */
        f.addListener(new ChannelFutureListener() {//添加对这个future的listener

            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
