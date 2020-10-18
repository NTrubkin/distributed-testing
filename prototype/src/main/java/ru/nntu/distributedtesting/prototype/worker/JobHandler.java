package ru.nntu.distributedtesting.prototype.worker;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.Handler;
import ru.nntu.distributedtesting.prototype.model.Job;
import ru.nntu.distributedtesting.prototype.model.JobResult;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

@RequiredArgsConstructor
public class JobHandler implements Handler {

    private final JobReadySender jobReadySender;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        Job body = (Job) container.getBody();
        System.out.println("body: " + body);

        // todo: perform the job
        var jobResult = new JobResult();
        jobResult.setSuccess(true);
        jobReadySender.send(jobResult, channel);
    }
}
