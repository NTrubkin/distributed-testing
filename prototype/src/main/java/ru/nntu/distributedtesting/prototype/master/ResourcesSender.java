package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.model.File;
import ru.nntu.distributedtesting.prototype.model.MessageType;
import ru.nntu.distributedtesting.prototype.model.Resources;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class ResourcesSender {

    private final Server server;
    private final MessageWriter messageWriter;

    public void send() {
        Resources resources = getResources();

        server.getClients()
              .forEach(client -> send(resources, client));

        System.out.println("Resources was sent to the clients");
    }

    private Resources getResources() {
        var file1 = new File();
        file1.setName("res-1.txt");
        file1.setContent("res-1-content".getBytes(UTF_8));

        var file2 = new File();
        file2.setName("res-2.txt");
        file2.setContent("res-2-content".getBytes(UTF_8));

        var resources = new Resources();
        resources.setFiles(List.of(file1, file2));
        return resources;
    }

    @SneakyThrows
    private void send(Resources resources, Channel channel) {
        messageWriter.write(MessageType.RESOURCES, resources, channel);
    }
}
