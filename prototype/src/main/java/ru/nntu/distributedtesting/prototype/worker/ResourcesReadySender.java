package ru.nntu.distributedtesting.prototype.worker;

import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.model.MessageType;

@RequiredArgsConstructor
public class ResourcesReadySender {

    private final Client client;
    private final MessageWriter messageWriter;

    public void send() {
        messageWriter.write(MessageType.RESOURCES_READY, client.getServerChannel());
        System.out.println("RESOURCES_READY was sent");
    }
}
