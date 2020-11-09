package ru.nntu.distributedtesting.common.model;

import lombok.Data;

@Data
public class JobResult implements MessageBody {

    private boolean isSuccess;
}
