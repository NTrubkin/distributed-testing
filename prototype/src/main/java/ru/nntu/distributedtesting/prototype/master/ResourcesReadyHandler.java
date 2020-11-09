package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.ChildHandler;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;
import ru.nntu.distributedtesting.prototype.model.MessageType;

@RequiredArgsConstructor
@Sharable
public class ResourcesReadyHandler implements ChildHandler {

    private final JobSender jobSender;

    @Override
    @SneakyThrows
    public void handle(MessageContainer container, Channel channel) {
        if (container.getType() != MessageType.RESOURCES_READY) {
            return;
        }

        jobSender.send(channel);
    }
}
