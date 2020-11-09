package ru.nntu.distributedtesting.prototypemavenplugin;

import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.model.MessageType;

@RequiredArgsConstructor
public class ResourcesReadySender {

    private final Client client;
    private final MessageWriter messageWriter;

    public void send() {
        messageWriter.write(MessageType.RESOURCES_READY, client.getMasterChannel());
        System.out.println("RESOURCES_READY was sent");
    }
}