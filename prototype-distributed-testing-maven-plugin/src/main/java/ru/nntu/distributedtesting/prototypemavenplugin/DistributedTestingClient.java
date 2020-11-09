package ru.nntu.distributedtesting.prototypemavenplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.MessageReader;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.RootHandler;
import ru.nntu.distributedtesting.common.model.Resources;

@RequiredArgsConstructor
public class DistributedTestingClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12000;
    private final Resources resources;

    public void start() {
        var client = new Client(HOST, PORT);

        var objectMapper = new ObjectMapper();
        var messageReader = new MessageReader(objectMapper);
        var messageWriter = new MessageWriter(objectMapper);

        var rootHandler = new RootHandler(messageReader);
        // rootHandler.getHandlers().put(MessageType.JOB, jobHandler);
        client.setRootHandler(rootHandler);

        client.start();

        new ResourcesFromClientSender(client, resources, messageWriter).send();
    }
}
