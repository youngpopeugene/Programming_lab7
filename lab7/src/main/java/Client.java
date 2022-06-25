package main.java;

import main.java.commands.Execute_script;
import main.java.util.Console;
import main.java.util.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;

public class Client {
    static final int PORT = 9880;
    private static final Console console = new Console();
    static ByteBuffer inputBuffer = ByteBuffer.allocate(10240);
    static ByteBuffer outputBuffer = ByteBuffer.allocate(10240);
    static boolean exitStatus = false;
    static boolean executeStatus = false;
    static private Socket socket = null;
    static private Command command = null;
    static SessionWorker sessionWorker = new SessionWorker(console);
    static Session session;
    private static final Validator validator = new Validator(console);

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println(Text.getGreenText("Connecting..."));
        while (!connect()) connect();

        while (true) {
            System.out.println(Text.getYellowText("Enter username:"));
            String username = console.nextLine();
            System.out.println(Text.getYellowText("Enter password:"));
            String password = console.nextLine();
            if (password.equals("")) password = null;
            session = sessionWorker.createSession(username, password);
            if (session == null) continue;
            try {
                sendSession(session);
                if (!receiveSessionStatus()) System.out.println(Text.getRedText("Authorization error!"));
                else {
                    System.out.println(Text.getGreenText("Authorization is successful!"));
                    break;
                }
            } catch (IOException e) {
                System.out.println(Text.getGreenText("Reconnecting..."));
                while (!connect()) connect();
                sendSession(session);
                if (!receiveSessionStatus()) System.out.println(Text.getRedText("Authorization error!"));
                else {
                    System.out.println(Text.getGreenText("Authorization is successful!"));
                    break;
                }
            }
        }

        validator.setSession(session);

        while (true) {
            boolean b;
            do b = write(); while (!b);
            if (exitStatus) break;
            if (!executeStatus) read();
        }

        console.close();
        try {
            socket.close();
        } catch (IOException | NullPointerException e) {
            System.out.println(Text.getRedText(""));
        }
    }

    private static boolean write() throws IOException, NoSuchElementException {
        executeStatus = false;
        if (!console.isReadFromFileStatus()) {
            System.out.println(Text.getBlueText("Enter the command:"));
        }
        String input = "";
        input = console.nextLine() + " ";
        command = validator.splitString(input);
        if (command != null && validator.validateCommand(command)) {
            if (command.getCommandName().equals("exit") && command.getArgName().equals("")) {
                exitStatus = true;
            }

            if (command.getCommandName().contains("add")
                    || command.getCommandName().equals("update")
                    || command.getCommandName().equals("remove_greater")
                    || command.getCommandName().equals("remove_lower")) {
                command.setHuman(validator.validateHuman());
            }

            if (command.getCommandName().equals("execute_script")) {
                new Execute_script(console).execute((String) command.getArgName());
                executeStatus = true;
            } else {
                outputBuffer.clear();
                outputBuffer.put(serialize(command));
                socket.getOutputStream().write(outputBuffer.array()); // here broken pipe could be
            }
        } else {
            if (!console.isReadFromFileStatus()) {
                System.out.println(Text.getRedText("Input or output error!"));
            }
            return false;
        }
        command.setUsername(session.username);
        return true;
    }

    static private void read() throws IOException, ClassNotFoundException {
        try {
            if (socket.getInputStream().read(inputBuffer.array()) == -1) {
                System.out.println(Text.getGreenText("Reconnecting..."));
                while (!connect()) connect();
                outputBuffer.put(serialize(command));
                socket.getOutputStream().write(outputBuffer.array());
                read();
                return;
            }
        } catch (SocketException e) {
            System.out.println(Text.getGreenText("Reconnecting..."));
            while (!connect()) connect();
            outputBuffer.put(serialize(command));
            socket.getOutputStream().write(outputBuffer.array());
            read();
            return;
        }

        ObjectInputStream objectInputStream =
                new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
        Respond respond = (Respond) objectInputStream.readObject();
        inputBuffer.clear();

        if (console.isReadFromFileStatus()) System.out.print("\n");
        System.out.println(respond.answer);
    }

    private static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    private static boolean connect() {
        try {
            socket = new Socket("localhost", PORT);
            System.out.println(Text.getGreenText("Successfully:)"));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void sendSession(Session session) throws IOException {
        outputBuffer.clear();
        outputBuffer.put(serialize(session));
        socket.getOutputStream().write(outputBuffer.array());
    }

    private static boolean receiveSessionStatus() throws IOException, ClassNotFoundException {
        if (socket.getInputStream().read(inputBuffer.array()) == -1) {
            throw new IOException();
        }
        ObjectInputStream objectInputStream =
                new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
        Respond respond = (Respond) objectInputStream.readObject();
        inputBuffer.clear();
        return Boolean.parseBoolean(respond.answer.trim());
    }
}