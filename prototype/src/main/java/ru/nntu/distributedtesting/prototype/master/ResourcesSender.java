package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.model.MessageType;
import ru.nntu.distributedtesting.common.model.Resources;

@RequiredArgsConstructor
public class ResourcesSender {

    private final Master master;
    private final MessageWriter messageWriter;

    public void send(Resources resources) {
        master.getWorkers()
              .forEach(worker -> send(resources, worker));

        System.out.println("Resources was sent to the workers");
    }

    @SneakyThrows
    private void send(Resources resources, Channel channel) {
        messageWriter.write(MessageType.RESOURCES, resources, channel);
    }
}
