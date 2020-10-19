package ru.nntu.distributedtesting.prototype.worker;

import io.netty.channel.Channel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.ChildHandler;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;
import ru.nntu.distributedtesting.prototype.model.MessageType;
import ru.nntu.distributedtesting.prototype.model.Resources;

@RequiredArgsConstructor
public class ResourcesHandler implements ChildHandler {

    private final ResourcesReadySender resourcesReadySender;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        if (container.getType() != MessageType.RESOURCES) {
            return;
        }
        Resources resources = (Resources) container.getBody();
        saveBase64Zip(resources.getBase64MainResources(), "C:/Users/trubk/Desktop/dt/worker/app-main-resources.jar");
        saveBase64Zip(resources.getBase64TestResources(), "C:/Users/trubk/Desktop/dt/worker/app-test-resources.jar");

        System.out.println("Resources received");

        resourcesReadySender.send();
    }

    @SneakyThrows
    public void saveBase64Zip(String base64Archive, String target) {
        byte[] archive = Base64.getDecoder().decode(base64Archive.getBytes());
        Path path = Paths.get(target);
        Files.write(path, archive);
    }
}
