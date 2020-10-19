package ru.nntu.distributedtesting.prototype.model;

import lombok.Data;

@Data
public class Resources implements MessageBody {

    // todo: try jackson default base64 encoding
    private String base64MainResources;
    private String base64TestResources;
}
