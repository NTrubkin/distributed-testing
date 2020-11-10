package ru.nntu.distributedtesting.prototype.worker;

import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.model.MessageType;

@RequiredArgsConstructor
public class ResourcesReadySender {

    private final Worker worker;
    private final MessageWriter messageWriter;

    public void send() {
        messageWriter.write(MessageType.RESOURCES_READY, worker.getMasterChannel());
        System.out.println("RESOURCES_READY was sent");
    }
}
