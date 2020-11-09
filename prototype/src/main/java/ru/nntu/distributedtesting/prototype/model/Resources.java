package ru.nntu.distributedtesting.prototype.model;

import lombok.Data;

@Data
public class Resources implements MessageBody {

    private byte[] mainResources;
    private byte[] testResources;
}
