package ru.nntu.distributedtesting.prototype.worker;

public class WorkerNodeApp {

    public static void main(String[] args) {
        Client client = new Client(12000);
        client.start();
        client.send("Hello");
    }
}
