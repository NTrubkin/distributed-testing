package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.ChildHandler;
import ru.nntu.distributedtesting.common.model.MessageContainer;

@RequiredArgsConstructor
@Sharable
public class ResourcesReadyHandler implements ChildHandler {

    private final JobSender jobSender;

    @Override
    @SneakyThrows
    public void handle(MessageContainer container, Channel channel) {
        jobSender.send(channel);
    }
}
