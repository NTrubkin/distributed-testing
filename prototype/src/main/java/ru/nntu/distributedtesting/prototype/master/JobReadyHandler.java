package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.ChildHandler;
import ru.nntu.distributedtesting.common.model.JobReport;
import ru.nntu.distributedtesting.common.model.MessageContainer;
import ru.nntu.distributedtesting.common.model.TaskReport;

@RequiredArgsConstructor
public class JobReadyHandler implements ChildHandler {

    private final Master master;
    private final TaskReportSender taskReportSender;

    @Override
    public void handle(MessageContainer container, Channel channel) {
        JobReport body = (JobReport) container.getBody();
        int workerId = master.getWorkers().indexOf(channel);
        System.out.println(body.isSuccess() ?
                "Job " + workerId + " success" :
                "Job " + workerId + " failed");
        master.finishJob(body.isSuccess());

        if (master.isTaskFinished()) {
            var report = new TaskReport();
            report.setSuccess(master.isTaskSuccess());
            taskReportSender.send(report);
        }
    }
}
