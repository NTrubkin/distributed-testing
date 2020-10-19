package ru.nntu.distributedtesting.prototype.worker;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.RootHandler;

@RequiredArgsConstructor
public class Client {

    private final String host;
    private final int port;
    private final EventLoopGroup group = new NioEventLoopGroup();

    @Setter
    private RootHandler rootHandler;

    @Getter
    private Channel serverChannel;

    @SneakyThrows
    public void start() {
        try {
            var bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline()
                                 .addLast(rootHandler);

                    // todo: remove this prototype hack
                    socketChannel.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(1000000));
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            serverChannel = channelFuture.channel();
        } catch (Exception e) {
            throw new RuntimeException("Client error occurred", e);
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }
}
