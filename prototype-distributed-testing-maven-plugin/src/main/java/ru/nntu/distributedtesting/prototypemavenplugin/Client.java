package ru.nntu.distributedtesting.prototypemavenplugin;

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
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.RootHandler;

@RequiredArgsConstructor
public class Client {

    private final String host;
    private final int port;
    private final EventLoopGroup group = new NioEventLoopGroup();

    @Setter
    private RootHandler rootHandler;

    @Getter
    private Channel masterChannel;

    @Getter
    private boolean isTaskSuccess = false;

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
            masterChannel = channelFuture.channel();
        } catch (Exception e) {
            throw new RuntimeException("Client error occurred", e);
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    @SneakyThrows
    public void awaitTermination() {
        // can't find awaitTermination without timeout
        group.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public void finishTask(boolean isSuccess) {
        isTaskSuccess = isSuccess;
        stop();
    }
}
