package ru.nntu.distributedtesting.prototype.worker;

import io.netty.channel.Channel;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.ChildHandler;
import ru.nntu.distributedtesting.prototype.model.Job;
import ru.nntu.distributedtesting.prototype.model.JobResult;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

@RequiredArgsConstructor
public class JobHandler implements ChildHandler {

    private final JobReadySender jobReadySender;
    private final String workerDir;

    @Override
    @SneakyThrows
    public void handle(MessageContainer container, Channel channel) {
        Job body = (Job) container.getBody();

        boolean isJobSuccess = runJob(body.getTestClasses());

        var jobResult = new JobResult();
        jobResult.setSuccess(isJobSuccess);
        jobReadySender.send(jobResult, channel);
    }

    @SneakyThrows
    private boolean runJob(List<String> testClasses) {
        Process process;
        boolean isWindows = System.getProperty("os.name")
                                  .toLowerCase()
                                  .startsWith("windows");
        if (isWindows) {
            var command = "java -cp " +
                    "\"" +
                    workerDir +"\\prototype-test-runner.jar;" +
                    workerDir +"\\app-main-resources.jar;" +
                    workerDir +"\\app-test-resources.jar" +
                    "\"" +
                    " ru.nntu.prototypetestrunner.TestRunnerApp " +
                    String.join(" ", testClasses);
            process = Runtime.getRuntime()
                             .exec(command);
        } else {
            throw new UnsupportedOperationException("not implemented yet");
        }
        StreamGobbler streamGobbler =
                new StreamGobbler(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        return exitCode == 0;
    }

    @RequiredArgsConstructor
    private static class StreamGobbler implements Runnable {

        private final InputStream inputStream;
        private final Consumer<String> consumer;

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                                                                  .forEach(consumer);
        }
    }
}
