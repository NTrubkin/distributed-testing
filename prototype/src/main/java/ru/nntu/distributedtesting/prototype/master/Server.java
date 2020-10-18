package ru.nntu.distributedtesting.prototype.master;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nntu.distributedtesting.prototype.RootHandler;

@RequiredArgsConstructor
public class Server {

    private final int port;
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    @Setter
    private RootHandler rootHandler;

    @Getter
    private final CopyOnWriteArraySet<Channel> clients = new CopyOnWriteArraySet<>();


    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         public void initChannel(SocketChannel ch) {
                             ch.pipeline()
                               .addLast(rootHandler);
                         }
                     })
                     .option(ChannelOption.SO_BACKLOG, 128)
                     .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            Channel channel = channelFuture.channel();
            channel.closeFuture();
        } catch (Exception e) {
            throw new RuntimeException("Server error occurred", e);
        }
    }

    public void stop() {
        childGroup.shutdownGracefully();
        parentGroup.shutdownGracefully();
    }
}
