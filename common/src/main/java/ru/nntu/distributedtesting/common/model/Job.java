package ru.nntu.distributedtesting.common.model;

import java.util.List;
import lombok.Data;

@Data
public class Job implements MessageBody {

    private List<String> testClasses;
}
