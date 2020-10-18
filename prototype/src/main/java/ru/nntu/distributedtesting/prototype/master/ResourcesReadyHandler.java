package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.prototype.Handler;
import ru.nntu.distributedtesting.prototype.model.Job;
import ru.nntu.distributedtesting.prototype.model.MessageContainer;
import ru.nntu.distributedtesting.prototype.model.MessageType;

@RequiredArgsConstructor
@Sharable
public class ResourcesReadyHandler implements Handler {

    private final JobSender jobSender;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        if (container.getType() != MessageType.RESOURCES_READY) {
            return;
        }

        var job = new Job();
        job.setTestClasses(List.of("Class1", "Class2"));

        jobSender.send(job, channel);
    }
}
