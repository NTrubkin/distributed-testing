package ru.nntu.distributedtesting.common.model;

import lombok.Data;

@Data
public class TaskReport implements MessageBody {

    private boolean isSuccess;
}
