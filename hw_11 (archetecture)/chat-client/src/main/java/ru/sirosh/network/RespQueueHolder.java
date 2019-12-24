package ru.sirosh.network;

import ru.sirosh.protocol.Request;

import java.util.Queue;

public class RespQueueHolder {
    Queue<String> queue;

    public synchronized Queue<String> getQueue() {
        return queue;
    }


    public RespQueueHolder(Queue<String> queue) {
        this.queue = queue;
    }
}
