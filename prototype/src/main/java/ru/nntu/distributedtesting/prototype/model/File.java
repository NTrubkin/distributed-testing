package ru.nntu.distributedtesting.prototype.model;

import lombok.Data;

@Data
public class File {

    private String name;
    private byte[] content;
}
