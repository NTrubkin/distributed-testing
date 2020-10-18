package ru.nntu.distributedtesting.prototype.worker;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.Handler;
import ru.nntu.distributedtesting.prototype.model.File;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;
import ru.nntu.distributedtesting.prototype.model.MessageType;
import ru.nntu.distributedtesting.prototype.model.Resources;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class ResourcesHandler implements Handler {

    private final ResourcesReadySender resourcesReadySender;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        if (container.getType() != MessageType.RESOURCES) {
            return;
        }
        Resources resources = (Resources) container.getBody();
        String resourcesBill = resources.getFiles()
                                        .stream()
                                        .map(File::getName)
                                        .collect(joining(", "));
        System.out.println("resources: " + resourcesBill);

        System.out.println("Resources received");

        // todo: save resources

        resourcesReadySender.send();
    }
}
