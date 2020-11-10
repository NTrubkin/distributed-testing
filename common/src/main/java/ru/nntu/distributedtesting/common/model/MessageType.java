package ru.nntu.distributedtesting.common.model;

// todo: fully rename enums because of new type of client (maven plugin)
public enum MessageType {

    HANDSHAKE,
    RESOURCES,
    RESOURCES_READY,
    JOB,
    JOB_READY,
    RESOURCES_FROM_CLIENT,
    TASK_REPORT
}
