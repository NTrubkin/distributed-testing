package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.model.Job;
import ru.nntu.distributedtesting.prototype.model.MessageType;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class JobSender {

    private final Master master;
    private final MessageWriter messageWriter;
    private final String appResDir;

    @SneakyThrows
    public void send(Channel channel) {

        List<String> testClasses = Files.list(Path.of(appResDir + "/app-test-res"))
                                        .map(Path::getFileName)
                                        .map(Path::toString)
                                        .filter(fileName -> fileName.endsWith(".class"))
                                        .map(fileName -> fileName.substring(0, fileName.length() - ".class".length()))
                                        .collect(toList());

        List<Channel> workers = master.getWorkers();
        int workerIndex = workers.indexOf(channel);

        List<String> testClassesForJob = IntStream.range(0, testClasses.size())
                                                  .filter(index -> index % workers.size() == workerIndex)
                                                  .mapToObj(testClasses::get)
                                                  .collect(toList());

        if (!testClassesForJob.isEmpty()) {
            var job = new Job();
            job.setTestClasses(testClassesForJob);
            messageWriter.write(MessageType.JOB, job, channel);
            System.out.println("Job " + workerIndex + " was sent");
        }
    }
}
