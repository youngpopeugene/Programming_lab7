package main.java.util;

import main.java.Server;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class Modules {
    static ByteBuffer receivingDataBuffer = ByteBuffer.allocate(10240);
    static ByteBuffer sendingDataBuffer = ByteBuffer.allocate(10240);
    static final Logger logger = Logger.getLogger(Server.class.getName());

    // модуль приема подключений
    public static void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serv = (ServerSocketChannel) key.channel();
        SocketChannel socket = serv.accept();
        socket.configureBlocking(false);
        socket.register(key.selector(), SelectionKey.OP_READ);
    }

    // модуль чтения запроса
    public static Object read(SelectionKey key) {
        try {
            SocketChannel sock = (SocketChannel) key.channel();
            if (sock.read(receivingDataBuffer) == -1) throw new IOException();
            receivingDataBuffer.flip();
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(receivingDataBuffer.array()));
            sock.configureBlocking(false);
            sock.register(key.selector(), SelectionKey.OP_WRITE);
            receivingDataBuffer.clear();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            key.cancel();
            logger.info(Text.getRedText("The client disconnected!"));
        }
        return null;
    }

    // модуль отправки ответов клиентов
    public static void write(Respond respond, SelectionKey key) {
        try {
            SocketChannel sock = (SocketChannel) key.channel();
            ByteBuffer x = ByteBuffer.allocate(10240);
            if (sock.read(x) == -1) throw new IOException();
            x.clear();
            sendingDataBuffer.put(serialize(respond));
            sendingDataBuffer.flip();
            sock.write(sendingDataBuffer);
            sock.configureBlocking(false);
            sock.register(key.selector(), SelectionKey.OP_READ);
            sendingDataBuffer.clear();
        } catch (IOException e) {
            key.cancel();
            logger.info(Text.getRedText("The client disconnected!"));
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return b.toByteArray();
        }
    }
}
