package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.ChildHandler;
import ru.nntu.distributedtesting.common.model.MessageContainer;
import ru.nntu.distributedtesting.common.model.Resources;

@RequiredArgsConstructor
@Sharable
public class ResourcesFromClientHandler implements ChildHandler {

    private final ResourcesSender sender;
    private final Master master;

    @Override
    @SneakyThrows
    public void handle(MessageContainer container, Channel channel) {
        System.out.println("Resources from client received");
        master.setCurrentJobResources((Resources) container.getBody());
        sender.send(master.getCurrentJobResources());
    }
}
