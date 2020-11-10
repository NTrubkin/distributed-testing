package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.ChildHandler;
import ru.nntu.distributedtesting.common.model.MessageContainer;

@RequiredArgsConstructor
public class HandshakeHandler implements ChildHandler {

    private final Master master;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        master.getWorkers().add(channel);
        System.out.println("New worker connected");
    }
}
