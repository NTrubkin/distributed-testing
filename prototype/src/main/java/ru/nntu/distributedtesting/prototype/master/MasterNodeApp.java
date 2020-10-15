package ru.nntu.distributedtesting.prototype.master;

import lombok.SneakyThrows;

public class MasterNodeApp {

    @SneakyThrows
    public static void main(String[] args) {
        var server = new Server(12000);
        server.start();
    }
}
