package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.ChildHandler;
import ru.nntu.distributedtesting.prototype.model.JobResult;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;

@RequiredArgsConstructor
public class JobReadyHandler implements ChildHandler {

    private final Master master;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        JobResult body = (JobResult) container.getBody();
        int workerId = master.getWorkers().indexOf(channel);
        System.out.println(body.isSuccess() ?
                "Job " + workerId + " success" :
                "Job " + workerId + " failed");
    }
}
