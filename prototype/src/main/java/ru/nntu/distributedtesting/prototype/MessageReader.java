package ru.nntu.distributedtesting.prototype;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

@RequiredArgsConstructor
public class MessageReader {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public MessageContainer read(ByteBuf buffer) {
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        return objectMapper.readValue(bytes, MessageContainer.class);
    }
}
