package ru.nntu.distributedtesting.prototype.master;

import lombok.RequiredArgsConstructor;
import ru.nntu.distributedtesting.common.MessageWriter;
import ru.nntu.distributedtesting.common.model.MessageType;
import ru.nntu.distributedtesting.common.model.TaskReport;

@RequiredArgsConstructor
public class TaskReportSender {

    private final Master master;
    private final MessageWriter messageWriter;

    public void send(TaskReport taskReport) {
        messageWriter.write(MessageType.TASK_REPORT, taskReport, master.getClient());
        master.closeTask();
        System.out.println("Task report was sent to the client");
    }
}
