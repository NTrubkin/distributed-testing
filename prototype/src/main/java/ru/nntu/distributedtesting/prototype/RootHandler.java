package ru.nntu.distributedtesting.prototype;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;
import ru.nntu.distributedtesting.prototype.model.MessageType;

@RequiredArgsConstructor
@Sharable
public class RootHandler extends ChannelInboundHandlerAdapter {

    @Getter
    private final Map<MessageType, ChildHandler> handlers = new ConcurrentHashMap<>();
    private final MessageReader messageReader;

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        MessageContainer container = messageReader.read((ByteBuf) message);

        ChildHandler childHandler = handlers.get(container.getType());

        if (childHandler != null) {
            childHandler.handle(container, context.channel());
        }
    }
}
