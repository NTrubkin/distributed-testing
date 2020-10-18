package ru.nntu.distributedtesting.prototype.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.MessageReader;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.RootHandler;
import ru.nntu.distributedtesting.prototype.model.MessageType;

public class MasterNodeApp {

    @SneakyThrows
    public static void main(String[] args) {
        var server = new Server(12000);

        var objectMapper = new ObjectMapper();
        var messageReader = new MessageReader(objectMapper);
        var messageWriter = new MessageWriter(objectMapper);

        var handshakeHandler = new HandshakeHandler(server);
        var jobSender = new JobSender(messageWriter);
        var resourcesReadyHandler = new ResourcesReadyHandler(jobSender);
        var jobReadyHandler = new JobReadyHandler();

        RootHandler rootHandler = new RootHandler(messageReader);
        rootHandler.getHandlers().put(MessageType.HANDSHAKE, handshakeHandler);
        rootHandler.getHandlers().put(MessageType.RESOURCES_READY, resourcesReadyHandler);
        rootHandler.getHandlers().put(MessageType.JOB_READY, jobReadyHandler);
        server.setRootHandler(rootHandler);

        server.start();

        Thread.sleep(Duration.ofSeconds(10).toMillis());
        new ResourcesSender(server, messageWriter).send();
    }
}
