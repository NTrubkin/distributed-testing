package ru.nntu.distributedtesting.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.model.MessageBody;
import ru.nntu.distributedtesting.common.model.MessageContainer;
import ru.nntu.distributedtesting.common.model.MessageType;


@RequiredArgsConstructor
public class MessageWriter {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T extends MessageBody> void write(MessageType type, T body, Channel channel) {
        var container = new MessageContainer();
        container.setType(type);
        container.setBody(body);
        byte[] bodyBytes = objectMapper.writeValueAsBytes(container);
        channel.writeAndFlush(Unpooled.wrappedBuffer(bodyBytes));
    }

    public void write(MessageType type, Channel channel) {
        write(type, null, channel);
    }
}
