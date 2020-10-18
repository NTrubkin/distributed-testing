package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.Handler;
import ru.nntu.distributedtesting.prototype.model.JobResult;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

@RequiredArgsConstructor
public class JobReadyHandler implements Handler {

    @Override
    public void handle(MessageContainer container, Channel channel) {
        JobResult body = (JobResult) container.getBody();

        System.out.println(body.isSuccess() ? "Job success" : "Job failed");
    }
}
