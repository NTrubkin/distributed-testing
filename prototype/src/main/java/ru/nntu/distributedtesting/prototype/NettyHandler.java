package ru.nntu.distributedtesting.prototype;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;

public class NettyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(Charset.defaultCharset());
        System.out.println("Received Message : " + message);
        if(message.equalsIgnoreCase("Hello")){
            ctx.writeAndFlush(Unpooled.wrappedBuffer("Hello There".getBytes()));
        }
        if(message.equalsIgnoreCase("Hello There")){
            ctx.writeAndFlush(Unpooled.wrappedBuffer("Thanks For Reply !!".getBytes()));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
