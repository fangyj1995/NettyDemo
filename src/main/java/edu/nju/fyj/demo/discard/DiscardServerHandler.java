package edu.nju.fyj.demo.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by yajfang on 2017/9/20.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {//当接受到消息时被调用
        ByteBuf in = (ByteBuf) msg;
        try {
            while(in.isReadable()) {
                System.out.print((char)in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
        //edu.nju.fyj.demo.discard the received data silently
       // ByteBuf 是 reference-counted object ， 必须手动释放
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {//当处理事件时发生异常或IO错误时被调用
        //close the connection when an exception is raised
        cause.printStackTrace();
        ctx.close();
    }
}
/*
ChannelInboundHandlerAdapter : an implementation of ChannelInboundHandler
ChannelInboundHandler： provides various event handler methods that you can override 提供用于重写的事件处理方法
 */