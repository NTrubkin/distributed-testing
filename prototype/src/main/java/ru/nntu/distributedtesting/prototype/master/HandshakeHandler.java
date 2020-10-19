package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.ChildHandler;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

@RequiredArgsConstructor
public class HandshakeHandler implements ChildHandler {

    private final Master master;
    private final int workersRequired;
    private final ResourcesSender sender;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        master.getWorkers().add(channel);
        System.out.println("New worker connected");

        if (master.getWorkers().size() == workersRequired) {
            sender.send();
        }
    }
}
