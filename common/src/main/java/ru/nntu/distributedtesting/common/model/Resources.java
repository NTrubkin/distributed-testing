package ru.nntu.distributedtesting.common.model;

import lombok.Data;

@Data
public class Resources implements MessageBody {

    private byte[] mainResources;
    private byte[] testResources;
}
