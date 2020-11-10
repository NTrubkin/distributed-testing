package ru.nntu.distributedtesting.prototypemavenplugin;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.ChildHandler;
import ru.nntu.distributedtesting.common.model.MessageContainer;
import ru.nntu.distributedtesting.common.model.TaskReport;

@RequiredArgsConstructor
public class TaskReportHandler implements ChildHandler {

    private final Client client;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        TaskReport taskReport = (TaskReport) container.getBody();
        client.finishTask(taskReport.isSuccess());
    }
}
