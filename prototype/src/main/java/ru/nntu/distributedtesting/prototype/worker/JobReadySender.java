package ru.nntu.distributedtesting.prototype.worker;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.model.JobReport;
import ru.nntu.distributedtesting.common.model.MessageType;

@RequiredArgsConstructor
public class JobReadySender {

    private final MessageWriter messageWriter;

    public void send(JobReport jobReport, Channel channel) {
        messageWriter.write(MessageType.JOB_READY, jobReport, channel);
        System.out.println("Job report was sent to the master");
    }
}
