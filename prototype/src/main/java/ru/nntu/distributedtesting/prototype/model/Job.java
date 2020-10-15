package ru.nntu.distributedtesting.prototype.model;

import java.util.List;
import lombok.Data;

@Data
public class Job {

    private List<String> testClasses;
}
