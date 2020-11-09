package ru.nntu.distributedtesting.prototypemavenplugin;

import io.netty.channel.Channel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.ChildHandler;
import ru.nntu.distributedtesting.common.model.MessageContainer;
import ru.nntu.distributedtesting.common.model.MessageType;
import ru.nntu.distributedtesting.common.model.Resources;

@RequiredArgsConstructor
public class ResourcesHandler implements ChildHandler {

    private final ResourcesReadySender resourcesReadySender;
    private final String workerDir;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        if (container.getType() != MessageType.RESOURCES) {
            return;
        }
        Resources resources = (Resources) container.getBody();
        saveZip(resources.getMainResources(), workerDir + "/app-main-resources.jar");
        saveZip(resources.getTestResources(), workerDir + "/app-test-resources.jar");

        System.out.println("Resources received");

        resourcesReadySender.send();
    }

    @SneakyThrows
    public void saveZip(byte[] archive, String target) {
        Path path = Paths.get(target);
        Files.write(path, archive);
    }
}
