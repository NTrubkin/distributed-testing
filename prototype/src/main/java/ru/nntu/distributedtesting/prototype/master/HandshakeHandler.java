package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.ChildHandler;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

@RequiredArgsConstructor
public class HandshakeHandler implements ChildHandler {

    private final Server server;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        server.getClients().add(channel);
        System.out.println("New client connected");
    }
}
