package ru.nntu.distributedtesting.common;

import io.netty.channel.Channel;
import ru.nntu.distributedtesting.common.model.MessageContainer;

public interface ChildHandler {

    void handle(MessageContainer container, Channel channel);
}
