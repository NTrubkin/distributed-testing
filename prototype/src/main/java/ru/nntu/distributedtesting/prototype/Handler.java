package ru.nntu.distributedtesting.prototype;

import io.netty.channel.Channel;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

public interface Handler {

    void handle(MessageContainer container, Channel channel);
}
