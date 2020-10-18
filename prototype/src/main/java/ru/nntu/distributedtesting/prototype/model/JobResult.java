package ru.nntu.distributedtesting.prototype.model;

import lombok.Data;

@Data
public class JobResult implements MessageBody {

    private boolean isSuccess;
}
