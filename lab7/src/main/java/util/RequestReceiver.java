package main.java.util;

import java.nio.channels.SelectionKey;
import java.util.concurrent.RecursiveTask;

public class RequestReceiver extends RecursiveTask<Object> {

    private final SelectionKey key;

    public RequestReceiver(SelectionKey key) {
        this.key = key;
    }

    @Override
    protected Object compute() {
        return Modules.read(key);
    }

}
