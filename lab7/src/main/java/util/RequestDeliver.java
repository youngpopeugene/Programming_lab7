package main.java.util;

import java.nio.channels.SelectionKey;

public class RequestDeliver implements Runnable {
    Respond respond;
    SelectionKey key;

    public RequestDeliver(Respond aRespond, SelectionKey aKey) {
        this.respond = aRespond;
        this.key = aKey;
    }

    @Override
    public void run() {
        Modules.write(respond, key);
    }
}
