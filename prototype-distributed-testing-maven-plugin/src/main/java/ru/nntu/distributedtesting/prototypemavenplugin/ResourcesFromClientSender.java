package ru.nntu.distributedtesting.prototypemavenplugin;

import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.model.MessageType;
import ru.nntu.distributedtesting.common.model.Resources;

@RequiredArgsConstructor
public class ResourcesFromClientSender {

    private final Client client;
    private final Resources resources;
    private final MessageWriter messageWriter;

    public void send() {
        messageWriter.write(MessageType.RESOURCES_FROM_CLIENT, resources, client.getMasterChannel());
        System.out.println("Resources from client was sent");
    }
}
