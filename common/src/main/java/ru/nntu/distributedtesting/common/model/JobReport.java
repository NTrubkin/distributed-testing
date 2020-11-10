package ru.nntu.distributedtesting.common.model;

import lombok.Data;

@Data
public class JobReport implements MessageBody {

    private boolean isSuccess;
}
