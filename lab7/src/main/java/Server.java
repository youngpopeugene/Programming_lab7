package main.java;

import main.java.collection.*;
import main.java.database.DBWorker;
import main.java.util.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;


public class Server {
    static final int PORT = 9880;
    final static Logger logger = Logger.getLogger(Server.class.getName());
    static CollectionManager collectionManager = new CollectionManager();

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        logger.info(Text.getGreenText("Entering server!"));

        DBWorker.init();
        logger.info(Text.getGreenText("Database connected!"));

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", PORT);
        serverSocketChannel.bind(inetSocketAddress);
        serverSocketChannel.configureBlocking(false);

        getCollection();
        logger.info(Text.getGreenText("Last session loaded!"));

        logger.info(Text.getGreenText("Waiting for clients to connect..."));

        while(true) {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selector.select(500);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        Modules.accept(key);
                        logger.info(Text.getGreenText("Connection accepted!"));
                    }
                    if (key.isReadable()) {
                        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() / 3);
                        Object inputObject = forkJoinPool.invoke(new RequestReceiver(key));
                        ExecutorService executorService = Executors.newCachedThreadPool();
                        executorService.submit(new RequestHandler(key, inputObject, collectionManager));
                    }
                }
            }
        }
    }

    private static void getCollection() {
        try {
            ResultSet data = DBWorker.getCollection();
            while (data.next()) {
                collectionManager.add(new HumanBeing(
                        data.getLong(1),
                        data.getString(2),
                        new Coordinates(data.getLong(3), data.getLong(4)),
                        LocalDateTime.of(data.getDate(5).toLocalDate(), data.getTime(6).toLocalTime()),
                        data.getBoolean(7),
                        data.getBoolean(8),
                        data.getString(9) != null ? data.getFloat(9) : null,
                        data.getLong(10),
                        data.getString(11) != null ? WeaponType.valueOf(data.getString(11)) : null,
                        Mood.valueOf(data.getString(12)),
                        new Car(data.getBoolean(13))

                ));
            }
        } catch (SQLException | NullPointerException e) {
            logger.warning("Collection is empty!");
        }
    }
}