package ru.nntu.distributedtesting.prototype.worker;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.NettyHandler;

@RequiredArgsConstructor
public class Client {

    private final int port;
    private final EventLoopGroup group = new NioEventLoopGroup();
    private ChannelFuture channelFuture;

    @SneakyThrows
    public void start() {
        try {
            var bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(new NettyHandler());
                }
            });
            channelFuture = bootstrap.connect("127.0.0.1", this.port).sync();
            channelFuture.channel();
        } catch (Exception e) {
            throw new RuntimeException("Client error occurred", e);
        }
    }

    public void send(String message) {
        if (channelFuture != null && channelFuture.isSuccess()) {
            channelFuture.channel()
                         .writeAndFlush(Unpooled.wrappedBuffer(message.getBytes()));
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }
}
