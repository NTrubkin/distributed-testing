package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.model.Job;
import ru.nntu.distributedtesting.prototype.model.MessageType;

@RequiredArgsConstructor
public class JobSender {

    private final MessageWriter messageWriter;

    public void send(Job job, Channel channel) {
        messageWriter.write(MessageType.JOB, job, channel);
        System.out.println("Job was sent");
    }
}
