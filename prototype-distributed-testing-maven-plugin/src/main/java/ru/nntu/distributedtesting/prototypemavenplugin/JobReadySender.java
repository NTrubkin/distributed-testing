package ru.nntu.distributedtesting.prototypemavenplugin;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.model.JobResult;
import ru.nntu.distributedtesting.common.model.MessageType;

@RequiredArgsConstructor
public class JobReadySender {

    private final MessageWriter messageWriter;

    public void send(JobResult jobResult, Channel channel) {
        messageWriter.write(MessageType.JOB_READY, jobResult, channel);
        System.out.println("Job result was sent");
    }
}
