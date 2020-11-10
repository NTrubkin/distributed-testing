package ru.nntu.distributedtesting.common.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Data
public class MessageContainer {

    private MessageType type;

    @JsonTypeInfo(use = NAME, property = "type")
    @JsonSubTypes({
            @Type(value = Resources.class, name = "RESOURCES"),
            @Type(value = Job.class, name = "JOB"),
            @Type(value = JobReport.class, name = "JOB_READY"),
            @Type(value = TaskReport.class, name = "TASK_REPORT"),
    })
    private MessageBody body;
}
