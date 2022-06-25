package main.java.util;

public class SessionWorker {
    private final Console console;

    public SessionWorker(Console aConsole) {
        console = aConsole;
    }

    public Session createSession(String username, String password) {
        return new Session(username, password);
    }
}
