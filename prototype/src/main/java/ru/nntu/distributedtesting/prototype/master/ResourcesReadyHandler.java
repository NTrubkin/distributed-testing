package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.ChildHandler;
import ru.nntu.distributedtesting.prototype.model.Job;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;
import ru.nntu.distributedtesting.prototype.model.MessageType;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Sharable
public class ResourcesReadyHandler implements ChildHandler {

    private final Server server;
    private final JobSender jobSender;

    @Override
    @SneakyThrows
    public void handle(MessageContainer container, Channel channel) {
        if (container.getType() != MessageType.RESOURCES_READY) {
            return;
        }

        var job = new Job();

        List<String> testClasses = Files.list(Path.of("C:/Users/trubk/Desktop/dt/input/app-test-res"))
                                        .map(Path::getFileName)
                                        .map(Path::toString)
                                        .filter(fileName -> fileName.endsWith(".class"))
                                        .map(fileName -> fileName.substring(0, fileName.length() - ".class".length()))
                                        .collect(toList());

        List<Channel> clients = server.getClients();
        int clientIndex = clients.indexOf(channel);

        List<String> testClassesForJob = IntStream.range(0, testClasses.size())
                                                  .filter(index -> index % clients.size() == clientIndex)
                                                  .mapToObj(testClasses::get)
                                                  .collect(toList());

        job.setTestClasses(testClassesForJob);

        jobSender.send(job, channel);
    }
}
