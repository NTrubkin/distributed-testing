package ru.nntu.distributedtesting.prototype.worker;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.model.JobResult;
import ru.nntu.distributedtesting.prototype.model.MessageType;

@RequiredArgsConstructor
public class JobReadySender {

    private final MessageWriter messageWriter;

    public void send(JobResult jobResult, Channel channel) {
        messageWriter.write(MessageType.JOB_READY, jobResult, channel);
        System.out.println("Job result was sent");
    }
}
