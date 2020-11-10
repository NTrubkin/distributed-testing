package ru.nntu.distributedtesting.prototype.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.MessageReader;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.RootHandler;
import ru.nntu.distributedtesting.common.model.MessageType;

public class MasterNodeApp {

    private static final int PORT = 12000;

    @SneakyThrows
    public static void main(String[] args) {
        var master = new Master(PORT);

        var objectMapper = new ObjectMapper();
        var messageReader = new MessageReader(objectMapper);
        var messageWriter = new MessageWriter(objectMapper);

        var resourcesSender = new ResourcesSender(master, messageWriter);
        var handshakeHandler = new HandshakeHandler(master);
        var jobSender = new JobSender(master, messageWriter);
        var resourcesReadyHandler = new ResourcesReadyHandler(jobSender);
        var reportSender = new TaskReportSender(master, messageWriter);
        var jobReadyHandler = new JobReadyHandler(master, reportSender);
        var resFromClientHandler = new ResourcesFromClientHandler(resourcesSender, master);

        RootHandler rootHandler = new RootHandler(messageReader);
        rootHandler.getHandlers().put(MessageType.HANDSHAKE, handshakeHandler);
        rootHandler.getHandlers().put(MessageType.RESOURCES_READY, resourcesReadyHandler);
        rootHandler.getHandlers().put(MessageType.JOB_READY, jobReadyHandler);
        rootHandler.getHandlers().put(MessageType.RESOURCES_FROM_CLIENT, resFromClientHandler);
        master.setRootHandler(rootHandler);

        master.start();
    }
}
