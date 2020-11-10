package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.Utils;
import ru.nntu.distributedtesting.common.model.Job;
import ru.nntu.distributedtesting.common.model.MessageType;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class JobSender {

    private final Master master;
    private final MessageWriter messageWriter;

    @SneakyThrows
    public void send(Channel channel) {
        List<String> testClasses = Utils.unzipFilesNames(master.getCurrentJobResources().getTestResources())
                                        .stream()
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
