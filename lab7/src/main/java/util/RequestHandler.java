package main.java.util;

import main.java.Server;
import main.java.collection.HumanBeing;
import main.java.database.DBWorker;

import java.nio.channels.SelectionKey;
import java.util.Collections;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {

    final private SelectionKey key;
    final private Object inputObject;
    static CollectionManager collectionManager;
    static Invoker invoker;
    final Logger logger = Logger.getLogger(Server.class.getName());

    public RequestHandler(SelectionKey aKey, Object aInputObject, CollectionManager aCollectionManager) {
        this.key = aKey;
        this.inputObject = aInputObject;
        collectionManager = aCollectionManager;
        invoker = new Invoker(collectionManager);
    }

    @Override
    public void run() {
        Respond respond;
        boolean flag = true;
        try {
            Command command = (Command) inputObject;
            logger.info(Text.getBlueText("Transferred command: ") + Text.getGreenText(command.getCommandName()) + " " + Text.getGreenText(command.getArgName()));
            respond = invoker.execute(command);

        } catch (Exception e) {
            Session session = (Session) inputObject;
            if (DBWorker.checkUser(session)) {
                if (DBWorker.checkPassword(session)) {
                    respond = new Respond(Text.getYellowText(session.getUsername()) + Text.getGreenText(" logged in!"));
                } else {
                    respond = new Respond(Text.getRedText("Wrong password for ") + Text.getYellowText(session.getUsername()) + Text.getRedText("!"));
                    flag = false;
                }
            } else {
                DBWorker.addUser(session);
                respond = new Respond(Text.getYellowText(session.getUsername()) + Text.getGreenText(" registered!"));
            }
        }
        logger.info(Text.getBlueText("Server response: ") + "\n" + Text.getNormalText(respond.answer));
        if (inputObject.getClass() == Session.class) respond = new Respond(String.valueOf(flag));
        Thread t = new Thread(new RequestDeliver(respond, key));
        t.start();

    }


}

