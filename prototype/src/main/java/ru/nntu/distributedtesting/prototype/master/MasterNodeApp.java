package ru.nntu.distributedtesting.prototype.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.MessageReader;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.RootHandler;
import ru.nntu.distributedtesting.prototype.model.MessageType;

public class MasterNodeApp {

    private static final int PORT = 12000;
    private static final int CLIENTS_REQUIRED = 2;
    private static final String APP_RES_DIR = "C:/Users/trubk/Desktop/dt/resources";

    @SneakyThrows
    public static void main(String[] args) {
        var master = new Master(PORT);

        var objectMapper = new ObjectMapper();
        var messageReader = new MessageReader(objectMapper);
        var messageWriter = new MessageWriter(objectMapper);

        var resourcesSender = new ResourcesSender(master, messageWriter, APP_RES_DIR);
        var handshakeHandler = new HandshakeHandler(master, CLIENTS_REQUIRED, resourcesSender);
        var jobSender = new JobSender(master, messageWriter, APP_RES_DIR);
        var resourcesReadyHandler = new ResourcesReadyHandler(jobSender);
        var jobReadyHandler = new JobReadyHandler(master);

        RootHandler rootHandler = new RootHandler(messageReader);
        rootHandler.getHandlers().put(MessageType.HANDSHAKE, handshakeHandler);
        rootHandler.getHandlers().put(MessageType.RESOURCES_READY, resourcesReadyHandler);
        rootHandler.getHandlers().put(MessageType.JOB_READY, jobReadyHandler);
        master.setRootHandler(rootHandler);

        master.start();
    }
}
