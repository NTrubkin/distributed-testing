package ru.nntu.distributedtesting.prototype.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nntu.distributedtesting.prototype.MessageReader;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.RootHandler;
import ru.nntu.distributedtesting.prototype.model.MessageType;

public class WorkerNodeApp {

    public static void main(String[] args) {
        var client = new Client("127.0.0.1", 12000);

        var objectMapper = new ObjectMapper();
        var messageReader = new MessageReader(objectMapper);
        var messageWriter = new MessageWriter(objectMapper);

        var resourcesReadySender = new ResourcesReadySender(client, messageWriter);
        var resourcesHandler = new ResourcesHandler(resourcesReadySender);
        var jobReadySender = new JobReadySender(messageWriter);
        var jobHandler = new JobHandler(jobReadySender);

        var rootHandler = new RootHandler(messageReader);
        rootHandler.getHandlers().put(MessageType.RESOURCES, resourcesHandler);
        rootHandler.getHandlers().put(MessageType.JOB, jobHandler);
        client.setRootHandler(rootHandler);

        client.start();

        new HandshakeSender(client, messageWriter).send();
    }
}
