package ru.nntu.distributedtesting.prototype.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nntu.distributedtesting.prototype.MessageReader;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.RootHandler;
import ru.nntu.distributedtesting.prototype.model.MessageType;

public class WorkerNodeApp {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12000;

    public static void main(String[] args) {
        String workerDir = args[0];

        var worker = new Worker(HOST, PORT);

        var objectMapper = new ObjectMapper();
        var messageReader = new MessageReader(objectMapper);
        var messageWriter = new MessageWriter(objectMapper);

        var resourcesReadySender = new ResourcesReadySender(worker, messageWriter);
        var resourcesHandler = new ResourcesHandler(resourcesReadySender, workerDir);
        var jobReadySender = new JobReadySender(messageWriter);
        var jobHandler = new JobHandler(jobReadySender, workerDir);

        var rootHandler = new RootHandler(messageReader);
        rootHandler.getHandlers().put(MessageType.RESOURCES, resourcesHandler);
        rootHandler.getHandlers().put(MessageType.JOB, jobHandler);
        worker.setRootHandler(rootHandler);

        worker.start();

        new HandshakeSender(worker, messageWriter).send();
    }
}
